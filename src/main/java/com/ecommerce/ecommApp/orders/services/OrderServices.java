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
import com.ecommerce.ecommApp.orders.Models.Orders;
import com.ecommerce.ecommApp.orders.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void placeOrder(Long customerID, List<ItemsDTO> productsOrdered) throws Exception {
        for (ItemsDTO item : productsOrdered) {
            Orders orders = getOrderInstance(customerID, item);
            orderRepository.save(orders);
            notifyUser(Arrays.asList(NotificationType.Text_SMS.toString(), NotificationType.EMAIL.toString()), orders);
        }
    }

    private Orders getOrderInstance(Long customerId, ItemsDTO item) {
        Orders orders = new Orders();
        orders.setCustomerID(customerId);
        orders.setProductID(item.getProductID());
        orders.setCost(item.getCost());
        orders.setQuantity(item.getQuantity());
        orders.setStatus(OrderStatus.Placed.toString());
        return orders;
    }


    public String getOrderStatus(String orderID) {
        Orders order = orderRepository.getOne(orderID);
        return order.getStatus();
    }

    public void updateOrderStatus(String orderID, OrderStatus status) {
        Orders order = orderRepository.getOne(orderID);
        order.setStatus(status.toString());
        orderRepository.save(order);
    }

    public Orders getOrderbyUUID(UUID orderID) {
        return orderRepository.getOne(orderID.toString());
    }

    private void notifyUser(List<String> modes, Orders order) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CustomerDto customer = objectMapper.readValue(Communication.sendGetRequest("http://localhost:3000/customer/"+order.getCustomerID())
                , CustomerDto.class);
        OrderPlaced orderPlaced = createOrderPlacedInstance(modes, order, customer);
        NotificationProducer notificationProducer = CommonsUtil.getNotificationProducer();
        notificationProducer.producerNotification(objectMapper.writeValueAsString(orderPlaced),
                EcommAppApplication.environment.getRequiredProperty("notification.order.placed.topic"));
    }

    private OrderPlaced createOrderPlacedInstance(List<String> modes, Orders order, CustomerDto customer) {
        OrderPlaced orderPlaced = new OrderPlaced();
        orderPlaced.setMode(modes);
        orderPlaced.setCustomerDto(customer);
        orderPlaced.setOrderID(order.getOrderID().toString());
        orderPlaced.setProductName("ABC Product");
        orderPlaced.setQuandity(order.getQuantity());
        orderPlaced.setTotalCost(order.getCost());
        return orderPlaced;
    }
}