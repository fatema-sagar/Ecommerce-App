package com.ecommerce.ecommApp.notifications.services;

import com.ecommerce.ecommApp.EcommAppApplication;
import com.ecommerce.ecommApp.notifications.NotificationUtil;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class UserRegisteredService extends Thread {

    private static final Logger log= LoggerFactory.getLogger(UserRegisteredService.class);

    String kafkaTopicName;
    Properties props;
    KafkaConsumer kafkaConsumer;

    public UserRegisteredService(String kafkaTopicName) {
        super();
        this.kafkaTopicName=kafkaTopicName;
    }

    @Override
    public void run() {
        super.run();
        props = NotificationUtil.getConsumerConfigs();
        kafkaConsumer=NotificationUtil.createConsumer(props,kafkaTopicName);
        log.info("User Registered Notification service is started");
        while(true)
        {
            final ConsumerRecords<Long, String> consumerRecords =
                    kafkaConsumer.poll(100);
            consumerRecords.forEach(record -> {
                // TODO use the twilio sdk
                final String payload=record.value();

            });
        }
    }
}
