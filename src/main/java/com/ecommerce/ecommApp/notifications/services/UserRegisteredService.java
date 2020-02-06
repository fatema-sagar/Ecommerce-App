package com.ecommerce.ecommApp.notifications.services;

import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.commons.pojo.notification.UserRegistered;
import com.ecommerce.ecommApp.notifications.NotificationUtil;
import com.ecommerce.ecommApp.notifications.handlers.Handler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

public class UserRegisteredService extends Thread {

    private static final Logger log = LoggerFactory.getLogger(UserRegisteredService.class);

    String kafkaTopicName;
    Properties props;
    KafkaConsumer kafkaConsumer;

    ObjectMapper objectMapper;
    Handler notificationHandler;

    public UserRegisteredService(String kafkaTopicName) {
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
        log.info("User Registered Notification service is started");

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    kafkaConsumer.poll(100);
            consumerRecords.forEach(record -> {
                        // TODO use the twilio sdk
                        final String json = record.value();
                        try {
                            UserRegistered userRegistered = objectMapper.readValue(json, UserRegistered.class);
                            System.out.println("record found : " + userRegistered.toString());
                            String message = formatMessage(userRegistered);
                            notificationHandler.sendNotification(getName(), userRegistered.getMode(), userRegistered, message);
                        } catch (IOException ex) {
                            log.error("error in processing json record in : " + getName());
                        }
                    }
            );
        }
    }

    public String formatMessage(UserRegistered userRegistered) {
        CustomerDto customerDto = userRegistered.getCustomerDto();
        return String.format(NotificationUtil.MessageTemplate.USER_REGISTERED_MESSAGE, customerDto.getId());
    }
}


//example : {"mode":["Text_SMS","EMAIL","WHATSAPP"],"customerDto":{"id":"ox1","name":"abc","number":8851530831,"email":"sagarbindal992@gmail.com","whatsapp":1234567890}}