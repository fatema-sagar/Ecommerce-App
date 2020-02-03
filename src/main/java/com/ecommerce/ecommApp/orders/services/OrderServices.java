package com.ecommerce.ecommApp.orders.services;

import com.ecommerce.ecommApp.EcommAppApplication;
import com.ecommerce.ecommApp.commons.NotificationProducer;
import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.commons.enums.NotificationType;
import com.ecommerce.ecommApp.commons.pojo.customer.Customer;
import com.ecommerce.ecommApp.commons.pojo.notification.OrderPlaced;
import com.ecommerce.ecommApp.commons.pojo.notification.UserRegistered;
import com.ecommerce.ecommApp.notifications.NotificationUtil;
import com.ecommerce.ecommApp.orders.Models.Items;
import com.ecommerce.ecommApp.orders.Models.Orders;
import com.ecommerce.ecommApp.commons.enums.OrderStatus;
import com.ecommerce.ecommApp.orders.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ecommerce.ecommApp.commons.enums.NotificationType.*;

@Service
public class OrderServices {
    @Autowired
    private OrderRepository orderRepository;

    public List<Orders> getAllOrder(long customerID) {
        List<Orders> allOrders = orderRepository.allOrdersofCustomer(customerID);
        return allOrders;
    }

    public void placeOrder(long customerID, List<Items> productsOrdered) {

        for (Items item:productsOrdered) {
            Orders orders =new Orders();
            orders.setCustomerID(customerID);
            orders.setProductID(item.getProductID());
            orders.setQuantity(item.getQuantity());
            orders.setCost(item.getCost());
            orders.setStatus(OrderStatus.Placed);
            orderRepository.save(orders);
            ObjectMapper objectMapper = new ObjectMapper();

            OrderPlaced orderPlaced=new OrderPlaced();
            //to be replaced by api to get customer
            Customer customer=new Customer();
            List<String> modes=new ArrayList<>();
            modes.add(Text_SMS.toString());
            modes.add(EMAIL.toString());
            Long l= 9899889988L;
            customer.setId(customerID);
            customer.setName("Sample User");
            customer.setEmail("sampleemail@mail.com");
            customer.setNumber(l);
            customer.setPassword("samplePassword");
            customer.setGender("Gender");
            customer.setWhatsapp(l);
            orderPlaced.setCustomer(customer);
            orderPlaced.setMode(modes);
            orderPlaced.setOrderID(orders.getOrderID().toString());
            //to be replaced by product Api
            orderPlaced.setProductName("SampleProduct");
            orderPlaced.setQuandity((int)orders.getQuantity());
            orderPlaced.setTotalCost((int)orders.getCost());

            NotificationProducer notificationProducer = CommonsUtil.getNotificationProducer();
            try {
                notificationProducer.producerNotification(objectMapper.writeValueAsString(orderPlaced),
                        EcommAppApplication.environment.getRequiredProperty(NotificationUtil.NOTIFICATION_ORDER_PLACED_TOPIC));
            } catch (IOException ex) {

            }
        }
    }

    public OrderStatus getOrderStatus(UUID orderID) {
        Orders order=orderRepository.getOne(orderID);
        return order.getStatus();
    }

    public void updateOrderStatus(UUID orderID, OrderStatus status) {
        Orders order= orderRepository.getOne(orderID);
        order.setStatus(status);
        orderRepository.save(order);

    }

    public Orders getOrderbyUUID(UUID orderID) {
        return orderRepository.getOne(orderID);
    }
}
