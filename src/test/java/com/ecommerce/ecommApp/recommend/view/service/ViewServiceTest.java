package com.ecommerce.ecommApp.recommend.view.service;

import com.ecommerce.ecommApp.commons.kafka.ProducerBuilder;
import com.ecommerce.ecommApp.commons.pojo.ResponseMessage;
import com.ecommerce.ecommApp.recommend.view.dto.ViewProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ViewServiceTest {

    @InjectMocks
    private ViewService viewService;

    @Mock
    private ProducerBuilder producerBuilder;

    @Mock
    private Environment environment;



    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void viewProductTest() {

        ViewProductDto viewProductDto = mock(ViewProductDto.class);
        Properties properties = mock(Properties.class);
        KafkaProducer<String, String> kafkaProducer = mock(KafkaProducer.class);

        when(producerBuilder.getProducerConfigs()).thenReturn(properties);
        when(producerBuilder.getKafkaProducer(any())).thenReturn(kafkaProducer);

        ResponseMessage response = viewService.viewProduct(viewProductDto);

        assertEquals(HttpStatus.OK, response.getHttpStatus());
        verify(producerBuilder, times(1)).getKafkaProducer(any());
    }

}
