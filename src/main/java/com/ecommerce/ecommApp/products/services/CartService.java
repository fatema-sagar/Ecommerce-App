package com.ecommerce.ecommApp.products.services;

import com.ecommerce.ecommApp.commons.pojo.products.Cart;
import com.ecommerce.ecommApp.products.composite.CartIdentity;
import com.ecommerce.ecommApp.products.payload.CartItem;
import com.ecommerce.ecommApp.products.repositories.CartRepository;
import com.ecommerce.ecommApp.products.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//import com.ecommerce.ecommApp.payloads.CartsPayload;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart addToCart(CartItem payload) {
        Cart cart = new Cart();
        CartIdentity cartIdentity = new CartIdentity(payload.getCustomerId(), payload.getProductId());
        cart.setCartIdentity(cartIdentity);
        cart.setQuantity(payload.getQuantity());
        cart.setCost(payload.getCost());
        cartRepository.save(cart);
        return cart;
    }

    public Cart deleteFromCart(CartIdentity identity) {
        Cart cart = cartRepository.findById(identity).get();
        cartRepository.delete(cart);
        return cart;
    }

    public List<Cart> getCart(Long customerId) {
        Optional<List<Cart>> fetchCartItems = cartRepository.findByCustomerId(customerId);
        List<Cart> fetchedCartItems = fetchCartItems.get();
        return fetchedCartItems;
    }

    public Cart updateCart(Long customerId, int quantity, Long productId) {
        List<Cart> updatedCart = cartRepository.findByCustomerId(customerId).get();
        Cart cart = new Cart();
//        updatedCart.setCart_id(cartId);
        for(Cart fetchedCartItem : updatedCart){
            if(fetchedCartItem.getCartIdentity().getProductid()==productId){
                fetchedCartItem.setQuantity(quantity);
                cartRepository.save(fetchedCartItem);
            }
        }
//        updatedCart.setQuantity(quantity);
//        cartRepository.save(updatedCart);
        return cart;

    }


}
