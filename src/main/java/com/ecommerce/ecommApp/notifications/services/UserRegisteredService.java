package com.ecommerce.ecommApp.notifications.services;

import com.ecommerce.ecommApp.notifications.NotificationUtil;
import com.ecommerce.ecommApp.pojo.notification.UserRegistered;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class UserRegisteredService extends Thread {

    private static final Logger log = LoggerFactory.getLogger(UserRegisteredService.class);

    String kafkaTopicName;
    Properties props;
    KafkaConsumer kafkaConsumer;

    ObjectMapper objectMapper;

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
        ;
        log.info("User Registered Notification service is started");
        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    kafkaConsumer.poll(100);
            consumerRecords.forEach(record -> {
                        // TODO use the twilio sdk
                        final String json = record.value();
                        try {
                            UserRegistered userRegistered = objectMapper.readValue(json, UserRegistered.class);
                            System.out.println("record found : "+ userRegistered.toString());
                        } catch (IOException ex) {
                            log.error("error in processing json record in : " + getName());
                        }
                    }
            );
        }
    }
}
