//package com.ecommerce.ecommApp.orders.services;
//
//import com.ecommerce.ecommApp.orders.Models.Items;
//import com.ecommerce.ecommApp.orders.Models.Orders;
//import com.ecommerce.ecommApp.commons.enums.OrderStatus;
//import com.ecommerce.ecommApp.orders.repository.OrderRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class OrderServices {
//    @Autowired
//    private OrderRepository orderRepository;
//
//    public List<Orders> getAllOrder(long customerID) {
//        List<Orders> allOrders = orderRepository.allOrdersofCustomer(customerID);
//        return allOrders;
//    }
//
//    public void placeOrder(long customerID, List<Items> productsOrdered) {
//
//        for (Items item:productsOrdered) {
//            Orders orders =new Orders();
//            orders.setCustomerID(customerID);
//            orders.setProductID(item.getProductID());
//            orders.setQuantity(item.getQuantity());
//            orders.setCost(item.getCost());
//            orders.setStatus(OrderStatus.Placed);
//            orderRepository.save(orders);
//        }
//    }
//
//    public OrderStatus getOrderStatus(UUID orderID) {
//        Orders order=orderRepository.getOne(orderID);
//        return order.getStatus();
//    }
//
//    public void updateOrderStatus(UUID orderID, OrderStatus status) {
//        Orders order= orderRepository.getOne(orderID);
//        order.setStatus(status);
//
//    }
//
//    public Orders getOrderbyUUID(UUID orderID) {
//        return orderRepository.getOne(orderID);
//    }
//}
