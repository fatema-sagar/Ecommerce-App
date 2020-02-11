package com.ecommerce.ecommApp.products.services;

import com.ecommerce.ecommApp.commons.pojo.products.Cart;
import com.ecommerce.ecommApp.payloads.CartsPayload;
import com.ecommerce.ecommApp.products.repositories.CartRepository;
import com.ecommerce.ecommApp.products.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart addToCart(CartsPayload payload) {
        Cart cart = new Cart();
        cart.setProductId(payload.getProductId());
        cart.setQuantity(payload.getQuantity());
        cart.setCustomerId(payload.getCustomerId());
        cartRepository.save(cart);
        return cart;
    }

    public Cart deleteFromCart(Long cartId) {
        Cart cartItem = cartRepository.findByCartId(cartId).get();
        cartRepository.delete(cartItem);
        return cartItem;
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

    }


}
