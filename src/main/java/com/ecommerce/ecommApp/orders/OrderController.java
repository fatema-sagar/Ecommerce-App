package com.ecommerce.ecommApp.orders;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    public static final Logger logger=(Logger) LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderServices orderService;

    @GetMapping("/{customerID}")
    public List<Orders> getAllOrdersofCustomer(@PathVariable long customerID ){
        List<Orders> orders =orderService.getAllOrder(customerID);
        return orders;
    }

    @PostMapping("/{customerID}")
    public void BookOrders(@RequestBody List<Items> ProductsOrdered,@PathVariable long customerID) {
        //extract the body and pass the list
        orderService.placeOrder(customerID,ProductsOrdered);
        //produce to kafka topic in order to place the Placed notification

    }
    @GetMapping("/{orderID}/status")
    public void updateStatus(@PathVariable UUID orderID ){
        //Return the status of the object;
        OrderStatus status = orderService.getOrderStatus(orderID);

    }
    @PatchMapping("/{orderID}/status")
    public void updateStatus(@RequestParam String status, @PathVariable UUID orderID){
        orderService.updateOrderStatus(orderID,status);
        //write custom query to update the order status of that orderid
        if ("Shipped".equals(status) || "Delivered".equals(status)) {
            // Write the notification  for shipped and delivered
        }


    }

    @GetMapping("/{orderID}")
    public Orders getSpecificOrder(@PathVariable UUID orderID){
        Orders order=orderService.getOrderbyUUID(orderID);
        return order;
    }


}
