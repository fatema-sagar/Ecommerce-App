package com.ecommerce.ecommApp.notifications.services;

import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.commons.pojo.notification.OrderPlaced;
import com.ecommerce.ecommApp.notifications.NotificationUtil;
import com.ecommerce.ecommApp.notifications.handlers.NotificationHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.Properties;

public class OrderPlacedService extends Thread {

    String kafkaTopicName;
    Properties props;
    KafkaConsumer kafkaConsumer;
    ObjectMapper objectMapper;
    NotificationHandler notificationHandler;

    public OrderPlacedService(String kafkaTopicName) {
        super();
        this.kafkaTopicName = kafkaTopicName;
    }

    @Override
    public void run() {
        super.run();
        props = NotificationUtil.getConsumerConfigs();
        kafkaConsumer = NotificationUtil.createConsumer(props, kafkaTopicName);
        objectMapper = new ObjectMapper();
        notificationHandler = NotificationUtil.getNotificationHandler();

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    kafkaConsumer.poll(100);
            consumerRecords.forEach(record -> {
                // TODO use the twilio sdk
                final String json = record.value();
                try {
                    OrderPlaced orderPlaced = objectMapper.readValue(json, OrderPlaced.class);
                    System.out.println("record found : " + orderPlaced.toString());
                    String message = formatMessage(orderPlaced);
                    notificationHandler.sendNotification(getName(), orderPlaced.getMode(), orderPlaced, message);
                } catch (IOException ex) {

                }
            });
        }
    }

    private String formatMessage(OrderPlaced orderPlaced) {
        CustomerDto customerDto = orderPlaced.getCustomerDto();
        return String.format(NotificationUtil.MessageTemplate.ORDER_PLACED_MESSAGE, orderPlaced.getQuandity(), orderPlaced.getProductName(), orderPlaced.getOrderID());
    }

}
