//package com.ecommerce.ecommApp.orders.controller;
//import ch.qos.logback.classic.Logger;
//import com.ecommerce.ecommApp.orders.Models.Items;
//import com.ecommerce.ecommApp.orders.Models.Orders;
//import com.ecommerce.ecommApp.orders.services.OrderServices;
//import com.ecommerce.ecommApp.commons.enums.OrderStatus;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/orders")
//public class OrderController {
//
//    public static final Logger logger=(Logger) LoggerFactory.getLogger(OrderController.class);
//    @Autowired
//    private OrderServices orderService;
//
//    @GetMapping("/{customerID}")
//    public List<Orders> getAllOrdersofCustomer(@PathVariable long customerID ){
//        return orderService.getAllOrder(customerID);
//    }
//
//    @PostMapping("/{customerID}")
//    public void BookOrders(@RequestBody List<Items> ProductsOrdered, @PathVariable long customerID) {
//        //extract the body and pass the list
//        orderService.placeOrder(customerID,ProductsOrdered);
//        //produce to kafka topic in order to place the Placed notification
//
//    }
//    @GetMapping("/{orderID}/status")
//    public void updateStatus(@PathVariable UUID orderID ){
//        //Return the status of the object;
//        OrderStatus status = orderService.getOrderStatus(orderID);
//
//    }
//    @PatchMapping("/{orderID}/status")
//    public void updateStatus(@RequestParam OrderStatus status, @PathVariable UUID orderID){
//        orderService.updateOrderStatus(orderID,status);
//        if (status==OrderStatus.Shipped || status==OrderStatus.Delivered) {
//            // Write the notification  for shipped and delivered
//        }
//    }
//
//    @GetMapping("/{orderID}")
//    public Orders getSpecificOrder(@PathVariable UUID orderID){
//        return orderService.getOrderbyUUID(orderID);
//    }
//
//
//}
