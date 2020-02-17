package com.ecommerce.ecommApp.kafka.consumer;

import com.ecommerce.ecommApp.EcommAppApplication;
import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;

@Component
public class Consumer {

    private Environment environment;

    @Autowired
    public Consumer() {
        environment = EcommAppApplication.environment;
    }

    private Properties getConsumerConfigs() {
        Properties props = new Properties();
        try {
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty
                    (CommonsUtil.KAFKA_BOOTSTRAP_SERVERS));
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                    StringDeserializer.class.getName());
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                    StringDeserializer.class.getName());
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "view-product-details");
        } catch (Exception ex) {
        }
        return props;
    }

    /**
     * This method will return a Kafka Comsumer Object
     *
     * @param props          : Kafka Consumer Properties
     * @param kafkaTopicName : Kafka Topic Name
     */
    public KafkaConsumer createConsumer(Properties props, String kafkaTopicName) {
        KafkaConsumer kafkaConsumer = new KafkaConsumer<Long, String>(props);
        kafkaConsumer.subscribe(Collections.singletonList(kafkaTopicName));
        return kafkaConsumer;

    }
}
