package com.ecommerce.ecommApp.invoice.invoiceGenerator.service;

import com.ecommerce.ecommApp.commons.Util.CommonsUtil;
import com.ecommerce.ecommApp.commons.kafka.ConsumerBuilder;
import com.ecommerce.ecommApp.commons.pojo.notification.OrderDetails;
import com.ecommerce.ecommApp.commons.pojo.orders.OrdersDTO;
import com.ecommerce.ecommApp.invoice.invoiceGenerator.pdfUtils.Utils;
import com.ecommerce.ecommApp.orders.services.OrderServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.Response;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
@Service
public class FetchOrderServiceImpl implements FetchOrderService {

    private ConsumerBuilder consumerBuilder;
    private KafkaConsumer kafkaConsumer;
    private Environment environment;
    private ObjectMapper objectMapper;
    private OrderServices orderServices;
    private InvoiceGeneratorServiceImpl invoiceGeneratorServiceImpl;

    /**
     * constructor for initialize the local variable
     *
     * @param consumerBuilder         provide KafkaConsumer
     * @param environment             for access the application properties value
     * @param orderServices           for getting the details of user order
     * @param invoiceGeneratorServiceImpl for generate the invoice
     */
    @Autowired
    public FetchOrderServiceImpl(ConsumerBuilder consumerBuilder, Environment environment, OrderServices orderServices,
                                 InvoiceGeneratorServiceImpl invoiceGeneratorServiceImpl) {

        this.consumerBuilder = consumerBuilder;
        this.environment = environment;
        this.objectMapper = CommonsUtil.getObjectMapper();
        this.orderServices = orderServices;
        this.invoiceGeneratorServiceImpl = invoiceGeneratorServiceImpl;
    }

    /**
     * method for fetching the record from order placed topic
     * then convert to OrdersDto which contain the details of order
     * call the invoiceGenerate method of invoiceGenerateService for generate the invoice
     */
    public void fetchOrder() {

        Properties properties = consumerBuilder.getProperties(Utils.GROUP_ID);
        this.kafkaConsumer = consumerBuilder.getKafkaConsumer(properties);

        log.info("Start the fetch service for consume the record from topic");
        kafkaConsumer.subscribe(Collections.singleton(
                environment.getProperty(CommonsUtil.NOTIFICATION_ORDER_PLACED_TOPIC)));


        while (true) {

            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(5));

            if (records.count() > 0) {
                records.forEach(record -> {
                    try {


                        OrderDetails orderDetails = objectMapper.readValue(record.value(), OrderDetails.class);
                        OrdersDTO ordersDTO = orderServices.getOrderDetails(orderDetails.getOrderID());
                        Response response = invoiceGeneratorServiceImpl.invoiceGenerate(ordersDTO);
                        log.info("Invoice for customerId {} with productId {} and sending status code {}", ordersDTO.getCustomerID(),
                                ordersDTO.getProductID(), response.getStatusCode());

                    } catch (JsonProcessingException e) {
                        log.error("JsonProcessingError : " + e.getCause());
                        log.error("Exception message : {}", e.getMessage());
                    } catch (NotFoundException e) {
                        log.error("Order not found with reason {} :  " +
                                "\n and Exception message : {}", e.getCause(), e.getMessage());
                    }

                });
            }
        }
    }

}
