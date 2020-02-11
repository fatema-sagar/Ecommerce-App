package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.commons.pojo.products.Cart;
import com.ecommerce.ecommApp.payloads.CartsPayload;
import com.ecommerce.ecommApp.products.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/carts", method = RequestMethod.POST)
    public ResponseEntity<Object> addToCart(@Valid @RequestBody CartsPayload payload) {
        Cart cart = cartService.addToCart(payload);
        return new ResponseEntity(cart, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/carts", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteFromCart(@RequestParam("cartId") Long cartId) {
        Cart cart = cartService.deleteFromCart(cartId);
        return ResponseEntity.ok(cart);
    }

    @RequestMapping(value = "/carts", method = RequestMethod.GET)
    public ResponseEntity<Object> getCustomerCart(@RequestParam("cartId") Long cartId) {
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cart);
    }

    @RequestMapping(value = "/carts", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCustomerCart(@RequestParam("cartId") Long cartId, @RequestParam("quantity") int quantity) {
        Cart cart = cartService.updateCart(cartId, quantity);
        return ResponseEntity.ok(cart);
    }
}



