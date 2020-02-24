package com.ecommerce.ecommApp.invoice.invoiceSend.controller;

import com.ecommerce.ecommApp.invoice.invoiceSend.dto.SendInvoiceDto;
import com.ecommerce.ecommApp.invoice.invoiceSend.service.SendInvoiceService;
import com.sendgrid.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/send")
public class InvoiceSendController {

    @Autowired
    private SendInvoiceService sendInvoiceService;


    @RequestMapping("/invoice")
    public ResponseEntity<Response> sendInvoice(@RequestBody SendInvoiceDto sendInvoiceDto) {

        log.trace("Called send invoice controller for email id {}", sendInvoiceDto.getTo());
        Response response = sendInvoiceService.sendInvoice(sendInvoiceDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
