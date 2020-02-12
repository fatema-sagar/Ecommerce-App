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

/**
 * @Author Krishna
 * This RestController will handle all the endpoints requests starting with /carts.
 */
@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * This is an Rest Api endpoint mapping which will handle the adding of item to the cart for a particular customer.
     * @return : Object of ResponseMessage class with a proper HTTP request status
     */
    @RequestMapping(value = "/carts", method = RequestMethod.POST)
    public ResponseEntity<Object> addToCart(@RequestBody CartItem cartItem) {
        Cart cart = cartService.addToCart(cartItem);
        return new ResponseEntity(cart, HttpStatus.CREATED);
    }

    /**
     * This is an Rest Api endpoint mapping which will handle the deleting of item from the cart for a particular customer Id.
     * @return : Object of ResponseMessage class with a proper HTTP request status
     */
    @RequestMapping(value = "/carts", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteFromCart(@RequestBody CartIdentity cartIdentity)
    {
        Cart cart = cartService.deleteFromCart(cartIdentity);
        return ResponseEntity.ok(cart);
    }

    /**
     * This is an Rest Api endpoint mapping which will return a list of Items present in the cart for a specific customer.
     * It expects a customer id as a path variable in the url.
     */
    @RequestMapping(value = "/carts/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCustomerCart( @PathVariable  Long customerId) {
       List<Cart> fetchedCart = cartService.getCart(customerId);
        return ResponseEntity.ok(fetchedCart);
    }

    /**
     * This is an Rest Api endpoint mapping which will handle the updating of a item already present in the cart for a particular customer.
     * @return : Object of ResponseMessage class with a proper HTTP request status
     */
    @RequestMapping(value = "/carts", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCustomerCart(@RequestBody CartItem updateCart) {
        Cart cart = cartService.updateCart(updateCart);
        return ResponseEntity.ok(cart);
    }
}



