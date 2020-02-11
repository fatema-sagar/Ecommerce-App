package com.ecommerce.ecommApp.notifications.services;

import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.commons.pojo.notification.OrderCancelled;
import com.ecommerce.ecommApp.notifications.NotificationUtil;
import com.ecommerce.ecommApp.notifications.handlers.NotificationHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class OrderCancelledService extends Thread {

    private static final Logger log=LoggerFactory.getLogger(OrderCancelledService.class);

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
        objectMapper = CommonsUtil.getObjectMapper();
        notificationHandler = new NotificationHandler();

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    kafkaConsumer.poll(100);
            consumerRecords.forEach(record -> {
                // TODO use the twilio sdk
                final String json = record.value();
                try {
                    OrderCancelled orderPlaced = objectMapper.readValue(json, OrderCancelled.class);
                    log.trace("Record Found : "+ orderPlaced.toString());
                    String message = formatMessage(orderPlaced);
                    notificationHandler.sendNotification(getName(), orderPlaced.getMode(), orderPlaced, message);
                } catch (IOException ex) {
                    log.error("Error in Processing record : {}",record);
                }
            });
        }
    }

    private String formatMessage(OrderCancelled orderPlaced) {
        return String.format(NotificationUtil.MessageTemplate.ORDER_CANCELLED_MESSAGE, orderPlaced.getQuandity(), orderPlaced.getProductName(), orderPlaced.getOrderID());
    }
}

