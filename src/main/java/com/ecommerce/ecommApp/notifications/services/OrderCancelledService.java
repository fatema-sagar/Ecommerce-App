package com.ecommerce.ecommApp.notifications.services;

import com.ecommerce.ecommApp.notifications.NotificationUtil;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Properties;

public class OrderCancelledService extends Thread {
    @Autowired
    Environment environment;

    String kafkaTopicName;
    Properties props;
    KafkaConsumer kafkaConsumer;

    public OrderCancelledService(String kafkaTopicName) {
        super();
        this.kafkaTopicName = kafkaTopicName;
    }

    @Override
    public void run() {
        super.run();
        props = NotificationUtil.getConsumerConfigs();
        kafkaConsumer = NotificationUtil.createConsumer(props, kafkaTopicName);
        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    kafkaConsumer.poll(100);
            consumerRecords.forEach(record -> {
                // TODO use the twilio sdk
                final String payload = record.value();

            });
        }
    }

}
