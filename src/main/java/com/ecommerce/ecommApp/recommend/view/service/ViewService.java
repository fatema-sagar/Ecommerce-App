package com.ecommerce.ecommApp.recommend.view.service;

import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.commons.kafka.ProducerBuilder;
import com.ecommerce.ecommApp.commons.pojo.ResponseMessage;
import com.ecommerce.ecommApp.recommend.view.dto.ViewProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Slf4j
@Service
public class ViewService {

    private ProducerBuilder producerBuilder;
    private String viewTopic;
    private ObjectMapper objectMapper;
    private Environment environment;

    /**
     * constructor for initialize the loca variable
     * @param producerBuilder object of producer which provide the kafka producer
     * @param environment Object of environment for access the value of application properties
     */
    @Autowired
    public ViewService(ProducerBuilder producerBuilder, Environment environment) {
        this.environment = environment;
        this.producerBuilder = producerBuilder;
        this.viewTopic = environment.getProperty(CommonsUtil.VIEW_PRODUCT_TOPIC);
        this.objectMapper = CommonsUtil.getObjectMapper();
    }

    /**
     * method for produce the view product
     * @param viewProductDto contains the details of product and user
     * @return object of ResponseMessage which contain the status and message
     */
    public ResponseMessage viewProduct(ViewProductDto viewProductDto) {

        try {

            String data = objectMapper.writeValueAsString(viewProductDto);
            Properties properties = producerBuilder.getProducerConfigs();
            KafkaProducer kafkaProducer = producerBuilder.getKafkaProducer(properties);
            producerBuilder.producerRecord(data, viewTopic, kafkaProducer);

        } catch (JsonProcessingException e) {

            log.error("Unable to save viewed product");
            log.error("JsonProcessingException : {}",e.getMessage());
            log.error("Exception cause : ", e.getCause());
            return new ResponseMessage(HttpStatus.EXPECTATION_FAILED, "Unable to save view product");

        }
        return new ResponseMessage(HttpStatus.OK, "Saved view product");
    }
}
