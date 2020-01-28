package com.ecommerce.ecommApp.notifications;

import org.apache.kafka.clients.consumer.KafkaConsumer;

public class NotificationService extends Thread {

    String kafkaTopicName;
    public NotificationService(String kafkaTopicName) {
        super();
        this.kafkaTopicName=kafkaTopicName;
    }

    @Override
    public void run() {
        super.run();

    }
}
