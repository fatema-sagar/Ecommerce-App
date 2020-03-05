package com.ecommerce.ecommApp.invoice.invoiceGenerator.service;

import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.commons.pojo.orders.OrdersDTO;
import com.ecommerce.ecommApp.customers.services.CustomerService;
import com.ecommerce.ecommApp.invoice.invoiceGenerator.converter.OrderDtoToInvoiceFormat;
import com.ecommerce.ecommApp.invoice.invoiceGenerator.dto.InvoiceFormatDto;
import com.ecommerce.ecommApp.invoice.invoiceSend.dto.SendInvoiceDto;
import com.ecommerce.ecommApp.invoice.invoiceSend.service.SendInvoiceService;
import com.ecommerce.ecommApp.invoice.invoiceSend.utils.SendUtils;
import com.sendgrid.Response;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class InvoiceGeneratorService {

    private PdfGenerateService pdfGenerateService;
    private OrderDtoToInvoiceFormat toInvoiceFormat;
    private CustomerService customerService;
    private SendInvoiceService sendInvoiceService;

    /**
     * constructor for initialize the local variable
     *
     * @param pdfGenerateService
     * @param orderDtoToInvoiceFormat
     * @param customerService
     * @param sendInvoiceService
     */
    @Autowired
    public InvoiceGeneratorService(PdfGenerateService pdfGenerateService,
                                   OrderDtoToInvoiceFormat orderDtoToInvoiceFormat, CustomerService customerService,
                                   SendInvoiceService sendInvoiceService) {
        this.pdfGenerateService = pdfGenerateService;
        this.toInvoiceFormat = orderDtoToInvoiceFormat;
        this.customerService = customerService;
        this.sendInvoiceService = sendInvoiceService;

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

        String filePath = pdfGenerateService.generatePdf(invoiceFormatDto);
        log.info("Generate invoice for customer id {} with pid {} and file path {}", invoiceFormatDto.getCustomerId(),
                invoiceFormatDto.getInvoiceDetails().getProductId(), filePath);

        Response response = sendInvoiceService.sendInvoice(getDto(filePath, invoiceFormatDto.getCustomerId()));
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
