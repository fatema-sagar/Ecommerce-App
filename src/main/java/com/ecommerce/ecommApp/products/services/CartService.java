package com.ecommerce.ecommApp.products.services;

import com.ecommerce.ecommApp.commons.pojo.products.Cart;
import com.ecommerce.ecommApp.commons.pojo.products.Product;
//import com.ecommerce.ecommApp.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommApp.products.controllers.CartController;
import com.ecommerce.ecommApp.products.repositories.CartRepository;
import com.ecommerce.ecommApp.products.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart addToCart(Long pid, int quantity) {
        Cart cart = new Cart();
        Product product = productRepository.findById(pid).get();
//        cart.getProduct().add(product);
//        product.getCart().add(cart);
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        return cart;

    }

    public Cart deleteFromCart(Long cartId) {

        Cart deletedC = cartRepository.findByCartId(cartId).get();
        cartRepository.delete(deletedC);
        return deletedC;

    }

    public Cart getCart(Long cartId) {
        return  cartRepository.findByCartId(cartId).orElse(new Cart());

    }

    public Cart updateCart(Long cartId, int quantity) {
        Cart updatedCart = cartRepository.findByCartId(cartId).get();
        updatedCart.setCart_id(cartId);
        updatedCart.setQuantity(quantity);
        cartRepository.save(updatedCart);
        return updatedCart;

//                 cartRepository.findByCartId(cartId);

//                cart.map(cart1 -> {
//                    cart1 = cart.get();
//                    cart1.setQuantity(quantity);
//                    cartRepository.save(cart1);
//                    return cart1;
//                }).orElseThrow(()-> new ResourceNotFoundException("Cart not found with id " + cartId));


    }


}
