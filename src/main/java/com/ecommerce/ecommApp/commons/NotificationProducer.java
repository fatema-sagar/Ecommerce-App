package com.ecommerce.ecommApp.commons;

import com.ecommerce.ecommApp.EcommAppApplication;
import com.ecommerce.ecommApp.notifications.NotificationUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class NotificationProducer
{

    public void producerNotification(String json,String kafkaTopicName)
    {
        Properties props=getProducerConfigs();
        KafkaProducer<String, String> kafkaProducer=new KafkaProducer<String, String>(props);
        ProducerRecord record=new ProducerRecord<>(kafkaTopicName, null, json);
        kafkaProducer.send(record);
    }

    public Properties getProducerConfigs()
    {
        Properties props=new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, EcommAppApplication.environment.getRequiredProperty(NotificationUtil.KAFKA_BOOTSTRAP_SERVERS));
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "id");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
    }
}
