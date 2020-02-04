package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.products.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController()
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping(value = "/carts")
    public ResponseEntity<Object> addToCart(@Valid @RequestBody Integer listOfPid[]){


}


}
