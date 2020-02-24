package com.ecommerce.ecommApp.invoice.invoiceGenerator.service;

import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.commons.kafka.Consumer;
import com.ecommerce.ecommApp.commons.pojo.notification.OrderPlaced;
import com.ecommerce.ecommApp.commons.pojo.orders.OrdersDTO;
import com.ecommerce.ecommApp.invoice.invoiceGenerator.pdfUtils.Utils;
import com.ecommerce.ecommApp.orders.services.OrderServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;

@Slf4j
@Service
public class FetchOrderService extends Thread {

    private Consumer consumer;
    private KafkaConsumer kafkaConsumer;
    private Environment environment;
    private ObjectMapper objectMapper;
    private OrderServices orderServices;
    private InvoiceGeneratorService invoiceGeneratorService;

    @Autowired
    public FetchOrderService(Consumer consumer, Environment environment, OrderServices orderServices,
                             InvoiceGeneratorService invoiceGeneratorService) {

        this.consumer = consumer;
        this.environment = environment;
        this.objectMapper = CommonsUtil.getObjectMapper();
        this.orderServices = orderServices;
        this.kafkaConsumer = consumer.getKafkaConsumer(Utils.GROUP_ID);
        this.invoiceGeneratorService = invoiceGeneratorService;
    }

    @Override
    public void run() {
        this.fetchOrder();
    }

    private void fetchOrder() {

        kafkaConsumer.subscribe(Collections.singleton(
                environment.getProperty(CommonsUtil.NOTIFICATION_ORDER_PLACED_TOPIC)));


        while (true) {

            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(5));

            if (records.count() > 0) {
                records.forEach(record -> {
                    try {

                        OrderPlaced orderPlaced = objectMapper.readValue(record.value(), OrderPlaced.class);
                        OrdersDTO ordersDTO = orderServices.getOrderDetails(orderPlaced.getOrderID());
                            invoiceGeneratorService.invoiceGenerate(ordersDTO);
                        log.info("Invoice send for customerId {} with productId {}", ordersDTO.getCustomerID(),
                                ordersDTO.getProductID());

                    } catch (JsonProcessingException e) {

                        log.error("JsonProcessingError : " + e.getCause());
                        e.printStackTrace();

                    } catch (NotFoundException e) {

                        log.error("Order not found : " + e.getCause() + "\n Message : " + e.getMessage());
                        e.printStackTrace();
                    }

                });
            }
        }
    }

}
