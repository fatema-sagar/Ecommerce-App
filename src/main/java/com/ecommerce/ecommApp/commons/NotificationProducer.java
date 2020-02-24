package com.ecommerce.ecommApp.commons;

import com.ecommerce.ecommApp.EcommAppApplication;
import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.notifications.NotificationUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Properties;

public class NotificationProducer {

    @Autowired
    private Environment environment;

    public void producerNotification(String json, String kafkaTopicName) {
        Properties props = getProducerConfigs();
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);
        ProducerRecord record = new ProducerRecord<>(kafkaTopicName, null, json);
        kafkaProducer.send(record);
        kafkaProducer.flush();
        kafkaProducer.close();
    }

    private Properties getProducerConfigs() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getRequiredProperty(CommonsUtil.KAFKA_BOOTSTRAP_SERVERS));
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "id : " + System.currentTimeMillis());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
    }
}
