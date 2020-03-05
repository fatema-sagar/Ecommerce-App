package com.ecommerce.ecommApp.commons.kafka;

import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.UUID;

@Slf4j
@Component
public class ProducerBuilder {

    @Autowired
    private Environment environment;

    /**
     * this method used for getting the configuration of kafka producer
     * @return object of properties which contain properties
     */
    public Properties getProducerConfigs() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getRequiredProperty(CommonsUtil.KAFKA_BOOTSTRAP_SERVERS));
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "id : " + UUID.randomUUID().toString());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
    }

    /**
     * method used for create the kafka producer object
     * @param properties set of configuration for creating the kafka producer
     * @return object of kafka producer
     */
    public KafkaProducer<String, String> getKafkaProducer(Properties properties) {
        return new KafkaProducer<>(properties);
    }

    /**
     * used for produce the record in kafka topic
     * callback method call when data is successfully write or throw an exception
     * @param json value what we have to store in kafka topic
     * @param kafkaTopicName topic name
     * @param kafkaProducer object of producer
     */
    public void producerRecord(String json, String kafkaTopicName, KafkaProducer<String, String> kafkaProducer) {
        ProducerRecord record = new ProducerRecord<>(kafkaTopicName, null, json);
        kafkaProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e == null) {
                    log.info("Successfully saved view product with {}", recordMetadata.offset());
                } else {
                    log.error("Error to store the view product in topic with message {}", e.getMessage());
                    log.error("Cause for exception {}", e.getCause());
                }
            }
        });
        kafkaProducer.flush();
    }

    /**
     * used for close the kafka producer
     * @param kafkaProducer object of kafka producer
     */
    public void closeProducer(KafkaProducer<String, String> kafkaProducer) {
        kafkaProducer.flush();
        kafkaProducer.close();
    }

}
