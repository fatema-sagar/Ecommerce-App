package com.ecommerce.ecommApp.invoice.invoiceGenerator.service;

import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.commons.pojo.orders.OrdersDTO;
import com.ecommerce.ecommApp.customers.services.CustomerService;
import com.ecommerce.ecommApp.invoice.invoiceGenerator.converter.OrderDtoToInvoiceFormat;
import com.ecommerce.ecommApp.invoice.invoiceGenerator.dto.InvoiceFormatDto;
import com.ecommerce.ecommApp.invoice.invoiceSend.dto.SendInvoiceDto;
import com.ecommerce.ecommApp.invoice.invoiceSend.service.SendInvoiceServiceImpl;
import com.ecommerce.ecommApp.invoice.invoiceSend.utils.SendUtils;
import com.sendgrid.Response;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class InvoiceGeneratorServiceImpl implements InvoiceGeneratorService {

    private PdfGenerateServiceImpl pdfGenerateServiceImpl;
    private OrderDtoToInvoiceFormat toInvoiceFormat;
    private CustomerService customerService;
    private SendInvoiceServiceImpl sendInvoiceServiceImpl;

    /**
     * constructor for initialize the local variable
     *
     * @param pdfGenerateServiceImpl
     * @param orderDtoToInvoiceFormat
     * @param customerService
     * @param sendInvoiceServiceImpl
     */
    @Autowired
    public InvoiceGeneratorServiceImpl(PdfGenerateServiceImpl pdfGenerateServiceImpl,
                                       OrderDtoToInvoiceFormat orderDtoToInvoiceFormat, CustomerService customerService,
                                       SendInvoiceServiceImpl sendInvoiceServiceImpl) {
        this.pdfGenerateServiceImpl = pdfGenerateServiceImpl;
        this.toInvoiceFormat = orderDtoToInvoiceFormat;
        this.customerService = customerService;
        this.sendInvoiceServiceImpl = sendInvoiceServiceImpl;

    }

    /**
     * method for generate the invoice in pdf format
     *
     * @param ordersDTO contain the details of order
     */
    public Response invoiceGenerate(OrdersDTO ordersDTO) {

        InvoiceFormatDto invoiceFormatDto = toInvoiceFormat.convertToInvoiceFormatDto(ordersDTO);

        log.trace("Convert record to invoice format for customerId {} and productId {}", invoiceFormatDto.getCustomerId(),
                invoiceFormatDto.getInvoiceDetails().getProductId());

        String filePath = pdfGenerateServiceImpl.generatePdf(invoiceFormatDto);
        log.info("Generate invoice for customer id {} with pid {} and file path {}", invoiceFormatDto.getCustomerId(),
                invoiceFormatDto.getInvoiceDetails().getProductId(), filePath);

        Response response = sendInvoiceServiceImpl.sendInvoice(getDto(filePath, invoiceFormatDto.getCustomerId()));
        log.info("Send invoice for customerId {} with status code {}", invoiceFormatDto.getCustomerId(),
                response.getStatusCode());

        return response;
    }

    /**
     * method for creating the object SendInvoiceDto which contain the details to be send
     *
     * @param filePath   file path of invoice
     * @param customerId unique id of user
     * @return object of SendInvoiceDto contains message, file, email of sender, email of recipient  etc...
     */
    private SendInvoiceDto getDto(String filePath, Long customerId) {

        CustomerDto customer = null;
        SendInvoiceDto sendInvoiceDto = new SendInvoiceDto();
        try {
            customer = customerService.getCustomerDetails(customerId);
            sendInvoiceDto.setFile(new File(filePath));
            sendInvoiceDto.setContentMessage(SendUtils.CONTENT_MESSAGE);
            sendInvoiceDto.setCustomerName(customer.getName());
            sendInvoiceDto.setSubject(SendUtils.SUBJECT);
            sendInvoiceDto.setTo(customer.getEmail());

        } catch (NotFoundException e) {
            sendInvoiceDto = null;
            log.error("Customer not found with cid : {}", customerId);
            log.error("Customer not fount with message {} and cause {}", e.getMessage(), e.getCause());
        }
        return sendInvoiceDto;
    }
}
