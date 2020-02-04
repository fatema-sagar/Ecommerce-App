package com.ecommerce.ecommApp.orders.services;

import com.ecommerce.ecommApp.EcommAppApplication;
import com.ecommerce.ecommApp.commons.NotificationProducer;
import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.commons.Util.Communication;
import com.ecommerce.ecommApp.commons.enums.NotificationType;
import com.ecommerce.ecommApp.commons.enums.OrderStatus;
import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.commons.pojo.notification.OrderPlaced;
import com.ecommerce.ecommApp.commons.pojo.orders.ItemsDTO;
import com.ecommerce.ecommApp.notifications.NotificationUtil;
import com.ecommerce.ecommApp.orders.Models.Orders;
import com.ecommerce.ecommApp.orders.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServices {
    @Autowired
    private OrderRepository orderRepository;

    public List<Orders> getAllOrder(long customerID) {
        return orderRepository.getOrdersByCustomerId(customerID);
    }

    public void placeOrder(Long customerID, List<ItemsDTO> productsOrdered) {

        for (ItemsDTO item : productsOrdered) {
//            Orders orders = new Orders(customerID, item.getProductID(), item.getQuantity(), item.getCost(), OrderStatus.Placed);
//            orderRepository.save(orders);
//            notifyUser(Arrays.asList(NotificationType.Text_SMS.toString(), NotificationType.EMAIL.toString()), orders);
        }
    }


    public OrderStatus getOrderStatus(UUID orderID) {
        Orders order = orderRepository.getOne(orderID);
        return order.getStatus();
    }

    public void updateOrderStatus(UUID orderID, OrderStatus status) {
        Orders order = orderRepository.getOne(orderID);
        order.setStatus(status);
        orderRepository.save(order);
    }

    public Orders getOrderbyUUID(UUID orderID) {
        return orderRepository.getOne(orderID);
    }

    private void notifyUser(List<String> modes, Orders order) {
//        ObjectMapper objectMapper = new ObjectMapper();
////        notification
//        OrderPlaced orderPlaced = new OrderPlaced();
//        String response = Communication.sendGetRequest("localhost:3000/customer/" + order.getCustomerID());
////        String productName = Communication.sendGetRequest("localhost:3000/prducts/"+order.getProductID());
//        try {
//            CustomerDto customer = objectMapper.readValue(response, CustomerDto.class);
//            orderPlaced.setCustomerDto(customer);
//            orderPlaced.setMode(modes);
//            orderPlaced.setOrderID(order.getOrderID().toString());
//            orderPlaced.setProductName("sample");
//            orderPlaced.setQuandity(order.getQuantity());
//            orderPlaced.setTotalCost(order.getCost());
//            NotificationProducer notificationProducer = CommonsUtil.getNotificationProducer();
//            notificationProducer.producerNotification(objectMapper.writeValueAsString(orderPlaced),
//                    EcommAppApplication.environment.getRequiredProperty(NotificationUtil.NOTIFICATION_ORDER_PLACED_TOPIC));
//        } catch (IOException io) {
//
//        }
    }
}