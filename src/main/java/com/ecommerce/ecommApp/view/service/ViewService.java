package com.ecommerce.ecommApp.view.service;

import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.view.dto.ViewProductDto;
import com.ecommerce.ecommApp.kafka.producer.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ViewService {

    private Logger logger = LoggerFactory.getLogger(ViewService.class);
    private Producer producer;
    private String viewTopic;
    private ObjectMapper objectMapper;
    private Environment environment;

    @Autowired
    public ViewService(Producer producer, Environment environment) {
        this.environment = environment;
        this.producer = producer;
        this.viewTopic = environment.getProperty(CommonsUtil.VIEW_PRODUCT_TOPIC);
        this.objectMapper = CommonsUtil.getObjectMapper();
    }

    public String viewProduct(ViewProductDto viewProductDto) {

        try {
            String data = objectMapper.writeValueAsString(viewProductDto);
            producer.produceViewProduct(data, viewTopic);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException : " + e.getMessage());
            throw new RuntimeException("Object convert exception");
        } catch (Exception e) {
            logger.error("Exception" + e.getMessage());
            throw new RuntimeException("Object convert exception");
        }
        return "Saved view product";
    }
}
