package com.ecommerce.ecommApp.billGenerator.service;

import com.ecommerce.ecommApp.billGenerator.dto.BillRequestDto;
import com.ecommerce.ecommApp.billGenerator.dto.InvoiceDetails;
import com.ecommerce.ecommApp.billGenerator.dto.InvoiceFormatDto;
import com.ecommerce.ecommApp.commons.pojo.orders.OrdersDTO;
import com.ecommerce.ecommApp.view.dto.response.ApiResponse;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
@Service
public class BillGeneratorService {

    @Autowired
    private FetchOrderService fetchOrderService;

    @Autowired
    private PdfGenerateService pdfGenerateService;


    public ApiResponse billGenerate(BillRequestDto billRequestDto) throws FileNotFoundException, DocumentException {

        List<OrdersDTO> ordersList = fetchOrderService.fetchOrder(billRequestDto);
        InvoiceFormatDto invoiceFormatDto = getDataObjectList();
        pdfGenerateService.generatePdf(invoiceFormatDto);

        return new ApiResponse(HttpStatus.OK, "Successfully get orders", ordersList);
    }

    private InvoiceFormatDto getDataObjectList(){

        InvoiceFormatDto invoiceFormat = new InvoiceFormatDto();
        InvoiceDetails invoiceDetails = new InvoiceDetails();
        invoiceFormat.setBillingAddress("address");
        invoiceFormat.setShippingAddress("address");
        invoiceFormat.setCustomerId(Long.valueOf(1));
        invoiceFormat.setCustomerName("amit");
        invoiceFormat.setInvoiceId(Long.valueOf(3));
        invoiceFormat.setSoldBy("amit");
        invoiceFormat.setTitle("Amazon");

        invoiceDetails.setBrand("b");
        invoiceDetails.setOrderId("1");
        invoiceDetails.setPrice(Long.valueOf(9));
        invoiceDetails.setProduct_description("hello");
        invoiceDetails.setProductId(Long.valueOf(1));
        invoiceDetails.setProductName("t-shirt");
        invoiceDetails.setProductImage("h");
        invoiceDetails.setQuantity(Long.valueOf(1));
        invoiceDetails.setTotalAmount(Long.valueOf(500));

        invoiceFormat.setInvoiceDetails(invoiceDetails);

        return invoiceFormat;

    }
}
