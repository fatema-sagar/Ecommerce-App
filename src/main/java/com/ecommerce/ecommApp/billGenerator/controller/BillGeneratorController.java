package com.ecommerce.ecommApp.billGenerator.controller;

import com.ecommerce.ecommApp.billGenerator.dto.BillRequestDto;
import com.ecommerce.ecommApp.billGenerator.service.BillGeneratorService;
import com.ecommerce.ecommApp.view.dto.response.ApiResponse;
import com.itextpdf.text.DocumentException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/invoice")
public class BillGeneratorController {

    @Autowired
    private BillGeneratorService billGeneratorService;

    @RequestMapping("/generator")
    public ResponseEntity<ApiResponse> billGenerator(@Valid @RequestBody BillRequestDto billRequestDto) throws IOException, DocumentException, NotFoundException {

        log.info("Invoice generator called with customer id : " + billRequestDto.getCustomerId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(billGeneratorService.billGenerate(billRequestDto));
    }
}
