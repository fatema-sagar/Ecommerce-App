package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.commons.pojo.products.Cart;
import com.ecommerce.ecommApp.payloads.CartsPayload;
import com.ecommerce.ecommApp.products.repositories.CartRepository;
import com.ecommerce.ecommApp.products.repositories.ProductRepository;
import com.ecommerce.ecommApp.products.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
//@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

//    @PostMapping()
    @RequestMapping(value = "/carts", method = RequestMethod.POST)
//    public ResponseEntity<Object> addToCart( @RequestParam("productId") Long productId,@RequestParam("customerId") Long customerId, @RequestParam("quantity") int quantity) {
    public ResponseEntity<Object> addToCart(@Valid @RequestBody CartsPayload payload) {
        Cart cart = cartService.addToCart(payload);
        return new ResponseEntity(cart, HttpStatus.CREATED);
    }

//    @DeleteMapping()
@RequestMapping(value = "/carts", method = RequestMethod.DELETE)
public ResponseEntity<Object> deleteFromCart(@RequestParam("cartId") Long cartId) {
        Cart cart = cartService.deleteFromCart(cartId);
        return ResponseEntity.ok(cart);


    }

//    @GetMapping()
@RequestMapping(value = "/carts", method = RequestMethod.GET)
public ResponseEntity<Object> getCartsofACustomer(@RequestParam("cartId") Long cartId) {
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cart);

    }

//    @PutMapping()
@RequestMapping(value = "/carts", method = RequestMethod.PUT)
public ResponseEntity<Object> updateCartofACustomer(@RequestParam("cartId") Long cartId, @RequestParam("quantity") int quantity) {

        Cart cart = cartService.updateCart(cartId, quantity);
        return ResponseEntity.ok(cart);
    }

}



