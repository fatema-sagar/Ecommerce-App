package com.ecommerce.ecommApp.billGenerator.service;

import com.ecommerce.ecommApp.billGenerator.converter.OrderDtoToInvoiceFormat;
import com.ecommerce.ecommApp.billGenerator.dto.BillRequestDto;
import com.ecommerce.ecommApp.billGenerator.dto.InvoiceFormatDto;
import com.ecommerce.ecommApp.commons.pojo.orders.OrdersDTO;
import com.ecommerce.ecommApp.view.dto.response.ApiResponse;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class BillGeneratorService {

    private FetchOrderService fetchOrderService;
    private PdfGenerateService pdfGenerateService;
    private OrderDtoToInvoiceFormat toInvoiceFormat;

    @Autowired
    public BillGeneratorService(FetchOrderService fetchOrderService, PdfGenerateService pdfGenerateService,
                                OrderDtoToInvoiceFormat orderDtoToInvoiceFormat) {
        this.pdfGenerateService = pdfGenerateService;
        this.fetchOrderService = fetchOrderService;
        this.toInvoiceFormat = orderDtoToInvoiceFormat;

    }


    public ApiResponse billGenerate(BillRequestDto billRequestDto) throws IOException, DocumentException {

        List<OrdersDTO> ordersList = fetchOrderService.fetchOrder(billRequestDto);
        List<InvoiceFormatDto> invoiceFormatDtoList = toInvoiceFormat.convertToInvoiceFormatDto(ordersList);

        for (InvoiceFormatDto invoiceFormatDto: invoiceFormatDtoList) {
            pdfGenerateService.generatePdf(invoiceFormatDto);
        }

        return new ApiResponse(HttpStatus.OK, "Successfully get orders", ordersList);
    }

}
