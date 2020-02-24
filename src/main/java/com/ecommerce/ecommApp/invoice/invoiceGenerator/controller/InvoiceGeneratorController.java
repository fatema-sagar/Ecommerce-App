package com.ecommerce.ecommApp.invoice.invoiceGenerator.controller;

import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.invoice.invoiceGenerator.dto.InvoiceRequestDto;
import com.ecommerce.ecommApp.invoice.invoiceGenerator.service.InvoiceGeneratorService;
import com.ecommerce.ecommApp.recommend.view.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping(CommonsUtil.INVOICE)
public class InvoiceGeneratorController {

    @Autowired
    private InvoiceGeneratorService invoiceGenerate;

    @RequestMapping(CommonsUtil.INVOICE_GENERATE)
    public ResponseEntity<ApiResponse> invoiceGenerator(@Valid @RequestBody InvoiceRequestDto invoiceRequestDto) {

        log.info("Invoice generator called with customer id : " + invoiceRequestDto.getCustomerId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceGenerate.invoiceGenerate(invoiceRequestDto));
    }
}
