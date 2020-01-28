package com.ecommerce.ecommApp.orders;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    public static final Logger logger=(Logger) LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderServices orderService;

    @GetMapping("/")
    public List<Orders> getAllOrdersofCustomer(){
    }

    @PostMapping("/")
    public void BookOrders() {
        //extract the body and pass the list
        //produce to kafka topic in order to place the Placed notification

    }

    @PostMapping("/{orderID}/status")
    public void updateStatus(){
        //write custom query to update the order status of that orderid
        // Write the notification  for shipped and delivered

    }

    @GetMapping("/{orderID}")
    public Orders getSpecificOrder(){
        Orders order=new Orders();
        return order;
    }


}
