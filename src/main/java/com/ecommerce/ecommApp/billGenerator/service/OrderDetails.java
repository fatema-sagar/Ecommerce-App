package com.ecommerce.ecommApp.billGenerator.service;

import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.kafka.consumer.Consumer;
import com.ecommerce.ecommApp.orders.Models.Orders;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.lang.invoke.ConstantCallSite;
import java.util.Collections;
import java.util.UUID;

@Service
public class OrderDetails {

    private Consumer consumer;
    private KafkaConsumer kafkaConsumer;

    @Autowired
    public OrderDetails(Consumer consumer, Environment environment) {
        this.consumer = consumer;
        kafkaConsumer = this.consumer.getKafkaConsumer(UUID.randomUUID().toString());
        kafkaConsumer.subscribe(Collections.singleton(environment.getProperty(CommonsUtil.NOTIFICATION_ORDER_PLACED_TOPIC)));
    }

    public Orders fetchOrderDetails() {


        return null;
    }
}
