package com.ecommerce.ecommApp.products.services;

import com.ecommerce.ecommApp.commons.exceptions.CustomerNotFoundException;
import com.ecommerce.ecommApp.commons.exceptions.NotEnoughQuantityException;
import com.ecommerce.ecommApp.commons.pojo.products.Cart;
import com.ecommerce.ecommApp.commons.pojo.products.Product;
import com.ecommerce.ecommApp.customers.repository.CustomerRepository;
import com.ecommerce.ecommApp.orders.Models.Orders;
import com.ecommerce.ecommApp.orders.repository.OrderRepository;
import com.ecommerce.ecommApp.orders.services.OrderServices;
import com.ecommerce.ecommApp.products.composite.CartIdentity;
import com.ecommerce.ecommApp.commons.exceptions.ElementNotFoundException;
import com.ecommerce.ecommApp.products.payload.CartItem;
import com.ecommerce.ecommApp.products.repositories.CartRepository;
import com.ecommerce.ecommApp.products.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.util.List;
import java.util.Optional;

/**
 * @Author Krishna
 * This class will work as a service class for the CartController.
 */
@Service
public class CartService {

    @Autowired
    private OrderServices orderServices;

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * This function adds requested productIds  to the cart of the customer
     *
     * @param payload CartItem as argument
     * @return :List of Objects of Cart which are added to the cart of customer
     */

    public Cart addToCart(CartItem payload) throws ElementNotFoundException {
        Cart cart = new Cart();
        CartIdentity cartIdentity;
        if (customerRepository.existsById(payload.getCustomerId()) && productRepository.existsById(payload.getProductId()))
            cartIdentity = new CartIdentity(payload.getCustomerId(), payload.getProductId());
        else
            throw new ElementNotFoundException("customerId or productId not present");
        cart.setCartIdentity(cartIdentity);
        cart.setQuantity(payload.getQuantity());
        Product product = productRepository.findById(payload.getProductId()).get();
        cart.setCost(product.getPrice() * payload.getQuantity());
        cartRepository.save(cart);
        return cart;
    }

    /**
     * This function deletes requested products from the Cart
     * Takes composite id CartIdentity as argument
     *
     * @param identity
     * @return :Object of Cart which is deleted
     */
    public Cart deleteFromCart(CartIdentity identity) throws ElementNotFoundException {
        Cart cart = new Cart();
        if (cartRepository.findById(identity).isPresent())
            cart = cartRepository.findById(identity).get();
        else throw new ElementNotFoundException("productId or customerId not present in the repository");
        cartRepository.delete(cart);
        return cart;
    }

    /**
     * This function fetches list of Cart for a particular customer
     * Takes customerId as argument
     *
     * @param customerId
     * @return
     */

    @Cacheable("cart")
    public List<Cart> getCart(Long customerId) throws CustomerNotFoundException {
        try {
            System.out.println("Going to sleep for 5 Secs.. to simulate backend call.");
            Thread.sleep(1000*5);
            Optional<List<Cart>> fetchCartItems = cartRepository.findByCustomerId(customerId);
            List<Cart> fetchedCartItems = fetchCartItems.get();
            return fetchedCartItems;
        } catch (Exception ex) {
            throw new CustomerNotFoundException("Customer with cartId= {} not found");
        }
    }

    /**
     * This function updates the quantity of a particular cart item of a customer.
     * Takes cartItem payload as argument
     *
     * @param payload
     * @return :Updated cart of the customer
     */

    public Cart updateCart(CartItem payload) throws ElementNotFoundException, NotEnoughQuantityException {
        List<Cart> check = cartRepository.existsByProductId(payload.getProductId());
        if (check.size() != 0) {
            Cart cart = cartRepository.findById(new CartIdentity(payload.getCustomerId(), payload.getProductId())).get();
            long productId = payload.getProductId();
            Product product = productRepository.findById(productId).get();
            if (checkProductAvailability(product, payload)) {
                cart.setQuantity(cart.getQuantity() + payload.getQuantity());
                cart.setCost(cart.getQuantity() * product.getPrice());
                return cartRepository.save(cart);
            } else {
                throw new NotEnoughQuantityException("Sorry for the inconvenience, but only the previous selected quantity is available");
            }
        } else {
            throw new ElementNotFoundException("Product not found in the cart.");
        }
    }

    private boolean checkProductAvailability(Product product, CartItem payload) {
        if (product.getProductId() == payload.getProductId() && product.getQuantity() >= payload.getQuantity())
            return true;
        return false;
    }

    public Orders checkoutCart(Long productId,Long customerId){
        Orders order = new Orders();
        Optional<List<Cart>> carts   = cartRepository.findByCustomerId(customerId);
        List<Cart> toBeOrderedCart = carts.get();

        for (Cart cart: toBeOrderedCart
             ) {
            order.setCustomerID(customerId);
            order.setProductID(productId);
            order.setQuantity(cart.getQuantity());
            order.setTotalCost(cart.getCost());
            order.setOrderStatus("orderPlaced");
            orderRepository.save(order);
        }
        return order;
    }

    /**
     * This function updates the quantity of a particular cart item of a customer.
     * Takes cartItem payload as argument
     * @param cartItem
     * @return : Updated cart of the customer
     */
    public Cart decreaseProducts(CartItem cartItem) throws NotEnoughQuantityException, ElementNotFoundException {
        List<Cart> check = cartRepository.existsByProductId(cartItem.getProductId());
        if (check.size() != 0) {
            Cart cart = cartRepository.findById(new CartIdentity(cartItem.getCustomerId(), cartItem.getProductId())).get();
            Product product = productRepository.findById(cartItem.getProductId()).get();
            if (cart.getQuantity() > cartItem.getQuantity()) {
                cart.setQuantity(cart.getQuantity() - cartItem.getQuantity());
                cart.setCost(cart.getQuantity() * product.getPrice());
                return cartRepository.save(cart);
            } else {
                throw new NotEnoughQuantityException("Sorry for the inconvenience, but only the previous selected quantity is available");
            }
        } else {
            throw new ElementNotFoundException("Product not found in the cart.");
        }
    }
}
