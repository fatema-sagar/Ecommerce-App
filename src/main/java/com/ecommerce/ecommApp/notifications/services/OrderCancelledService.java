package com.ecommerce.ecommApp.notifications.services;

import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.commons.pojo.notification.OrderCancelled;
import com.ecommerce.ecommApp.notifications.NotificationUtil;
import com.ecommerce.ecommApp.notifications.handlers.NotificationHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.Properties;

public class OrderCancelledService extends Thread {
    @Autowired
    Environment environment;

    String kafkaTopicName;
    Properties props;
    KafkaConsumer kafkaConsumer;
    ObjectMapper objectMapper;
    NotificationHandler notificationHandler;

    public OrderCancelledService(String kafkaTopicName) {
        super();
        this.kafkaTopicName = kafkaTopicName;
    }

    @Override
    public void run() {
        super.run();
        props = NotificationUtil.getConsumerConfigs();
        kafkaConsumer = NotificationUtil.createConsumer(props, kafkaTopicName);
        objectMapper = new ObjectMapper();
        notificationHandler = new NotificationHandler();

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    kafkaConsumer.poll(100);
            consumerRecords.forEach(record -> {
                // TODO use the twilio sdk
                final String json = record.value();
                try {
                    OrderCancelled orderPlaced = objectMapper.readValue(json, OrderCancelled.class);
                    System.out.println("record found : " + orderPlaced.toString());
                    String message = formatMessage(orderPlaced);
                    notificationHandler.sendNotification(getName(), orderPlaced.getMode(), orderPlaced, message);
                } catch (IOException ex) {

                }
            });
        }
    }

    private String formatMessage(OrderCancelled orderPlaced) {
        CustomerDto customerDto = orderPlaced.getCustomerDto();
        return String.format(NotificationUtil.MessageTemplate.ORDER_CANCELLED_MESSAGE, orderPlaced.getQuandity(), orderPlaced.getProductName(), orderPlaced.getOrderID());
    }
}

