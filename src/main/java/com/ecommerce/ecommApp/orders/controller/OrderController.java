package com.ecommerce.ecommApp.orders.controller;

import com.ecommerce.ecommApp.commons.enums.OrderStatus;
import com.ecommerce.ecommApp.commons.pojo.orders.ItemsDTO;
import com.ecommerce.ecommApp.orders.Models.Orders;
import com.ecommerce.ecommApp.orders.services.OrderServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderServices orderService;

    //get order by customer id api
    @GetMapping("/{customerID}")
    public List<Orders> getOrderByCustomerId(@PathVariable long customerID) {
        return orderService.getAllOrder(customerID);
    }

    // place order api
    @PostMapping("/{customerID}")
    public void placeOrder(@RequestBody List<ItemsDTO> productsOrdered, @PathVariable long customerID) throws Exception{
        //extract the body and pass the list
        orderService.placeOrder(customerID, productsOrdered);
        //produce to kafka topic in order to place the Placed notification
    }

    //check mapping
    @GetMapping("/{orderID}/status")
    public void getOrderStatus(@PathVariable UUID orderID) {
        //Return the status of the object;
        OrderStatus status = orderService.getOrderStatus(orderID);
    }

    @PatchMapping("/{orderID}/status")
    public void updateStatus(@RequestParam OrderStatus status, @PathVariable UUID orderID) {
        orderService.updateOrderStatus(orderID, status);
    }

    @GetMapping("/{orderID}")
    public Orders getSpecificOrder(@PathVariable UUID orderID) {
        return orderService.getOrderbyUUID(orderID);
    }


}
