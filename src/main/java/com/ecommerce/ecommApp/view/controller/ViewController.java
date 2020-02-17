package com.ecommerce.ecommApp.view.controller;

import com.ecommerce.ecommApp.view.dto.ViewProductDto;
import com.ecommerce.ecommApp.view.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private ViewService viewService;

    @PostMapping("/product")
    public ResponseEntity viewProduct(@RequestBody ViewProductDto viewProductDto) {
        String response="";
        try {
             response = viewService.viewProduct(viewProductDto);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
