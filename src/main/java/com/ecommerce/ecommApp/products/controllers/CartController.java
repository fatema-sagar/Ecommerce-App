package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.commons.pojo.products.Cart;
import com.ecommerce.ecommApp.products.repositories.CartRepository;
import com.ecommerce.ecommApp.products.repositories.ProductRepository;
import com.ecommerce.ecommApp.products.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping()
    public ResponseEntity<Object> addToCart(@Valid @RequestBody Long pid, @Valid @RequestBody int quantity) {
        Cart cart = cartService.addToCart(pid, quantity);
        return new ResponseEntity(cart, HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteFromCart(@Valid @RequestBody Long cartId) {
        Cart cart = cartService.deleteFromCart(cartId);
        return ResponseEntity.ok(cart);


    }

    @GetMapping()
    public ResponseEntity<Object> getCartsofACustomer(@Valid @RequestBody Long cartId) {
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cart);

    }

    @PutMapping()
    public ResponseEntity<Object> updateCartofACustomer(@Valid @RequestBody Long cartId, @Valid @RequestBody int quantity) {

        Cart cart = cartService.updateCart(cartId, quantity);
        return ResponseEntity.ok(cart);
    }

}



