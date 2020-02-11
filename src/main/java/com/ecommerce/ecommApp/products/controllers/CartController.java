package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.commons.pojo.products.Cart;
import com.ecommerce.ecommApp.products.composite.CartIdentity;
import com.ecommerce.ecommApp.products.payload.CartItem;
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
    public ResponseEntity<Object> addToCart(@Valid @RequestBody CartItem cartItem) {
        Cart cart = cartService.addToCart(cartItem);
        return new ResponseEntity(cart, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/carts", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteFromCart(@RequestBody CartIdentity cartIdentity)
    {
        Cart cart = cartService.deleteFromCart(cartIdentity);
        return ResponseEntity.ok(cart);
    }

    @RequestMapping(value = "/carts/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCustomerCart( @PathVariable  Long customerId) {
       List<Cart> fetchedCart = cartService.getCart(customerId);
        return ResponseEntity.ok(fetchedCart);
    }

    @RequestMapping(value = "/carts", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCustomerCart(@RequestParam("customerId") Long customerId, @RequestParam("quantity") int quantity, @RequestParam("productId") Long productId) {
        Cart cart = cartService.updateCart(customerId, quantity,productId);
        return ResponseEntity.ok(cart);
    }
}



