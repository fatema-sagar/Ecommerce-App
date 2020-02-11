package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.commons.pojo.products.Cart;
//import com.ecommerce.ecommApp.products.payload.CartsPayload;
import com.ecommerce.ecommApp.products.payload.CartsPayload;
import com.ecommerce.ecommApp.products.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/carts", method = RequestMethod.POST)
    public ResponseEntity<Object> addToCart(@Valid @RequestBody CartsPayload cartsPayload) {
        Cart cart = cartService.addToCart(cartsPayload);
        return new ResponseEntity(cart, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/carts", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteFromCart(@RequestParam("customerId") Long customerId,@RequestParam("productId") Long productId)
    {
        Cart cart = cartService.deleteFromCart(customerId,productId);
        return ResponseEntity.ok(cart);
    }

    @RequestMapping(value = "/carts", method = RequestMethod.GET)
    public ResponseEntity<Object> getCustomerCart(@RequestParam("customerId") Long customerId) {
       List<Cart> fetchedCart = cartService.getCart(customerId);
        return ResponseEntity.ok(fetchedCart);
    }

    @RequestMapping(value = "/carts", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCustomerCart(@RequestParam("customerId") Long customerId, @RequestParam("quantity") int quantity, @RequestParam("productId") Long productId) {
        Cart cart = cartService.updateCart(customerId, quantity,productId);
        return ResponseEntity.ok(cart);
    }
}



