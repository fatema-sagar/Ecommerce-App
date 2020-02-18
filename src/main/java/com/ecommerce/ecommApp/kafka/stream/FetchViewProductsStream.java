package com.ecommerce.ecommApp.kafka.stream;

import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.view.dto.ViewProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@Service
public class FetchViewProductsStream {

    @Autowired
    private Environment environment;

    private Properties getStreamProperties() {
        Properties properties = new Properties();

        String applicationId = UUID.randomUUID().toString();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty(CommonsUtil.KAFKA_BOOTSTRAP_SERVERS));
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);

        return properties;
    }

    public List<ViewProductDto> start(String customerId) {
        List<ViewProductDto> viewProducts = new ArrayList<>();
        Properties properties = getStreamProperties();
        KafkaStreams kafkaStreams = createTopology(properties, viewProducts, customerId);

        kafkaStreams.cleanUp();
        kafkaStreams.start();

        try {
            Thread.sleep(5000);
            kafkaStreams.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

        return viewProducts;
    }

    private KafkaStreams createTopology(Properties properties, List<ViewProductDto> list, String customerId) {

        StreamsBuilder builder = new StreamsBuilder();
        Serdes.StringSerde stringSerde = new Serdes.StringSerde();

        builder
                .stream(environment.getProperty(CommonsUtil.VIEW_PRODUCT_TOPIC), Consumed.with(stringSerde, stringSerde))
                .filter((key, value) -> isValidView(value, customerId, list))
                .peek((key, value) -> log.info("Viewed products : " + value + "\n"));

        return new KafkaStreams(builder.build(), properties);
    }

    private Boolean isValidView(String value, String customerId, List<ViewProductDto> list) {
        ObjectMapper objectMapper = CommonsUtil.getObjectMapper();
        try {
            ViewProductDto viewProductDto = objectMapper.readValue(value, ViewProductDto.class);
            if( viewProductDto.getCustomerId().equalsIgnoreCase(customerId)) {
                list.add(viewProductDto);
                return true;
            }
            return false;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json parse exception ");
        }
    }
}
