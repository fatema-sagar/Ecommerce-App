package com.ecommerce.ecommApp.notifications;

import com.ecommerce.ecommApp.EcommAppApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Collections;
import java.util.Properties;

public class NotificationUtil
{
    public static Environment environment= EcommAppApplication.environment;

    public static String KAFKA_BOOTSTRAP_SERVERS="kafka.bootstrap.server";

    public static String NOTIFICATION_REGISTERED_TOPIC="notification.registered.topic";
    public static String NOTIFICATION_ORDER_PLACED_TOPIC="notification.order.placed.topic";
    public static String NOTIFICATION_ORDER_STATUS_TOPIC="notification.order.status.topic";
    public static String NOTIFICATION_ORDER_CANCEL_TOPIC="notification.order.cancel.topic";

    public static Properties getConsumerConfigs()
    {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty
                (KAFKA_BOOTSTRAP_SERVERS));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaNotificationService");
        return props;
    }

    public static KafkaConsumer createConsumer(Properties props, String kafkaTopicName)
    {
        KafkaConsumer kafkaConsumer=new KafkaConsumer<Long, String>(props);
        kafkaConsumer.subscribe(Collections.singletonList(kafkaTopicName));
        return kafkaConsumer;
    }

    public static String getApplicationProperties(String key)
    {
        return environment.getRequiredProperty(key);
    }


}
