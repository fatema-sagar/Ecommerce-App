package com.ecommerce.ecommApp.orders.controller;

import com.ecommerce.ecommApp.commons.pojo.ResponseMessage;
import com.ecommerce.ecommApp.commons.pojo.orders.ItemsDTO;
import com.ecommerce.ecommApp.commons.pojo.orders.OrdersDTO;
import com.ecommerce.ecommApp.orders.Models.Orders;
import com.ecommerce.ecommApp.orders.services.OrderServices;
import javassist.NotFoundException;
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
    public ResponseEntity<Object> getOrderByCustomerId(@PathVariable long customerID) {
        try {
            return new ResponseEntity<>(orderService.getAllOrder(customerID), HttpStatus.OK);
        }
        catch (NotFoundException err)
        {
            return new ResponseEntity<>(new ResponseMessage("No order forund for customer id:" + customerID, "Error"), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/{customerID}", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> placeOrder(@RequestBody List<ItemsDTO> orderedProducts, @PathVariable long customerID) throws Exception {
        try {
            orderService.placeOrder(customerID, orderedProducts);
            return new ResponseEntity<>(new ResponseMessage("Success order placed", "created"), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage("Error in placing order", "Error"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/status/{orderID}")
    public ResponseEntity<Object> getOrderDetail(@PathVariable String orderID) {
        try {
            OrdersDTO ordersDTO = orderService.getOrderDetails(orderID);
            return new ResponseEntity<>(ordersDTO, HttpStatus.OK);
        }
        catch (NotFoundException e){
            return  new ResponseEntity<>(new ResponseMessage("Element with "+ orderID + "not found. Ensure the correct order id is provided","Error"),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{orderID}")
    public ResponseEntity<ResponseMessage> updateStatus(@RequestBody OrdersDTO updateOrder,@PathVariable String orderID) {
        try {
            String message = orderService.updateOrderStatus(orderID, updateOrder);
            return new ResponseEntity<>(new ResponseMessage(message, "Updated"), HttpStatus.OK);
        }
        catch (NotFoundException err){
            return new ResponseEntity<>(new ResponseMessage("Order not found", "Error"), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseMessage("The following error occurred "+e.getMessage(), "Error"), HttpStatus.BAD_REQUEST);
        }
    }

}
