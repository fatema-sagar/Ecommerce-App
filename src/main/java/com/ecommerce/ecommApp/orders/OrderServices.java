package com.ecommerce.ecommApp.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServices {
    @Autowired
    private OrderRepository orderRepository;
}
