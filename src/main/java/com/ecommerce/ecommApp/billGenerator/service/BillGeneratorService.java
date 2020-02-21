package com.ecommerce.ecommApp.billGenerator.service;

import com.ecommerce.ecommApp.billGenerator.converter.OrderDtoToInvoiceFormat;
import com.ecommerce.ecommApp.billGenerator.dto.BillRequestDto;
import com.ecommerce.ecommApp.billGenerator.dto.InvoiceFormatDto;
import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.commons.pojo.orders.OrdersDTO;
import com.ecommerce.ecommApp.customers.services.CustomerService;
import com.ecommerce.ecommApp.invoiceSend.dto.SendInvoiceDto;
import com.ecommerce.ecommApp.invoiceSend.service.SendInvoiceService;
import com.ecommerce.ecommApp.invoiceSend.utils.SendUtils;
import com.ecommerce.ecommApp.view.dto.response.ApiResponse;
import com.itextpdf.text.DocumentException;
import com.sendgrid.Response;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class BillGeneratorService {

    private FetchOrderService fetchOrderService;
    private PdfGenerateService pdfGenerateService;
    private OrderDtoToInvoiceFormat toInvoiceFormat;
    private CustomerService customerService;
    private HttpServletRequest request;
    private WebClient.Builder builder;

    @Autowired
    public BillGeneratorService(FetchOrderService fetchOrderService, PdfGenerateService pdfGenerateService,
                                OrderDtoToInvoiceFormat orderDtoToInvoiceFormat, CustomerService customerService,
                                HttpServletRequest request, WebClient.Builder builder) {
        this.pdfGenerateService = pdfGenerateService;
        this.fetchOrderService = fetchOrderService;
        this.toInvoiceFormat = orderDtoToInvoiceFormat;
        this.customerService = customerService;
        this.builder = builder;
        this.request = request;

    }


    public ApiResponse billGenerate(BillRequestDto billRequestDto) throws IOException, DocumentException, NotFoundException {

        Map<Long, Integer> responseStatus = new HashMap<>();


        List<OrdersDTO> ordersList = fetchOrderService.fetchOrder(billRequestDto);
        log.trace("Fetch {} orders for customer Id {} ", ordersList.size(), billRequestDto.getCustomerId());

        List<InvoiceFormatDto> invoiceFormatDtoList = toInvoiceFormat.convertToInvoiceFormatDto(ordersList);
        log.trace("Convert {} record to invoice format ", invoiceFormatDtoList.size());


        for (InvoiceFormatDto invoiceFormatDto: invoiceFormatDtoList) {

            String filePath = pdfGenerateService.generatePdf(invoiceFormatDto);
            Optional<Response> response = sendMail(filePath, billRequestDto.getCustomerId());
            responseStatus.put(invoiceFormatDto.getInvoiceDetails().getProductId(), response.get().getStatusCode());
        }

        log.info("Successfully send {} ", invoiceFormatDtoList.size());
        return new ApiResponse(HttpStatus.OK, "Successfully send invoice", responseStatus);
    }

    private SendInvoiceDto getDto(File file, Long customerId) throws NotFoundException {

        CustomerDto customer = customerService.getCustomerDetails(customerId);

        SendInvoiceDto sendInvoiceDto = new SendInvoiceDto();

        sendInvoiceDto.setContentMessage(SendUtils.CONTENT_MESSAGE);
        sendInvoiceDto.setCustomerName(customer.getName());
        sendInvoiceDto.setSubject(SendUtils.SUBJECT);
        sendInvoiceDto.setFile(file);
        sendInvoiceDto.setTo(customer.getEmail());
        return sendInvoiceDto;
    }

    private String getBaseUrl() {

        StringBuilder address = new StringBuilder();

        address.append(request.getScheme());
        address.append("://");
        address.append(request.getLocalName().substring(4));
        address.append(":");
        address.append(request.getLocalPort());
        return address.toString();
    }

    private Optional<Response> sendMail(String filePath, Long customerId) throws NotFoundException {

        WebClient.Builder builder = this.builder;
        Optional<Response> response = builder
                .baseUrl(getBaseUrl())
                .build()
                .method(HttpMethod.GET)
                .uri(SendUtils.SEND_CONTROLLER_PATH)
                .body(BodyInserters.fromPublisher(Mono.just(getDto(new File(filePath), customerId)), SendInvoiceDto.class))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(Charset.forName("UTF-8"))
                .retrieve()
                .bodyToMono(Response.class)
                .blockOptional();
        return response;
    }


}
