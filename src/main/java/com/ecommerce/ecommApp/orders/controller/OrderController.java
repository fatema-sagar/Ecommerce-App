package com.ecommerce.ecommApp.orders.controller;

import com.ecommerce.ecommApp.commons.pojo.orders.ItemsDTO;
import com.ecommerce.ecommApp.commons.pojo.orders.OrdersDTO;
import com.ecommerce.ecommApp.orders.Models.Orders;
import com.ecommerce.ecommApp.orders.services.OrderServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(path = "/{customerID}", method = RequestMethod.POST)
    public ResponseEntity<String> placeOrder(@RequestBody List<ItemsDTO> orderedProducts, @PathVariable long customerID) throws Exception {
        orderService.placeOrder(customerID, orderedProducts);
        return new ResponseEntity<String>("Success order placed", HttpStatus.OK);
    }

    @GetMapping("/status/{orderID}")
    public ResponseEntity<OrdersDTO> getOrderDetail(@PathVariable String orderID) {
        OrdersDTO ordersDTO=orderService.getOrderDetails(orderID);
        return new ResponseEntity<OrdersDTO>(ordersDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{orderID}")
    public ResponseEntity<String> updateStatus(@RequestBody OrdersDTO updateOrder,@PathVariable String orderID) {
        String message=orderService.updateOrderStatus(orderID, updateOrder);
        return new ResponseEntity<String>(message,HttpStatus.OK);
    }

}
