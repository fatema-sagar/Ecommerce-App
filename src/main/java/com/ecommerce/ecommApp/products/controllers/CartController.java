package com.ecommerce.ecommApp.products.controllers;

import com.ecommerce.ecommApp.commons.pojo.products.Cart;
import com.ecommerce.ecommApp.products.repositories.CartRepository;
import com.ecommerce.ecommApp.products.repositories.ProductRepository;
import com.ecommerce.ecommApp.products.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;



    @PostMapping(value = "/carts")
    public ResponseEntity<Object> addToCart(@Valid @RequestBody Long pid) {
     Cart cart = new Cart();
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping(value = "/carts")
    public ResponseEntity<Object> deleteFromCart(@Valid @RequestBody Long cartId){
        Cart cart = new Cart();
        cart  = cartService.deleteFromCart(cartId);
        return ResponseEntity.ok(cart);


        }

     @GetMapping(value = "/carts")
    public ResponseEntity<Object> getCartsofACustomer(@Valid @RequestBody Long cartId){
        Cart cart = new Cart();
        cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cart);

}

    @PutMapping(value ="/carts" )
    public ResponseEntity<Object> updateCartofACustomer(@Valid @RequestBody Long cartId, @Valid @RequestBody int quantity){

            Cart cart = new Cart();
            cart = cartService.updateCart(cartId,quantity  );
            return ResponseEntity.ok(cart);
        }

    }



