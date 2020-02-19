package com.ecommerce.ecommApp.billGenerator.service;

import com.ecommerce.ecommApp.billGenerator.dto.BillRequestDto;
import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.customers.models.Customer;
import com.ecommerce.ecommApp.customers.services.CustomerService;
import com.ecommerce.ecommApp.orders.Models.Orders;
import com.ecommerce.ecommApp.view.dto.response.ApiResponse;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BillGeneratorService {

    @Autowired
    private FetchOrderService fetchOrderService;

    @Autowired
    private CustomerService customerService;

    public ApiResponse billGenerate(BillRequestDto billRequestDto) {

        try {
            CustomerDto customer = customerService.getCustomerDetails(billRequestDto.getCustomerId());
        } catch (NotFoundException e) {
            log.error("Customer not fount");
            new RuntimeException("Customer not fount : " + e.getMessage()  + "\nCause : " + e.getCause());
        }
        List<Orders> ordersList = fetchOrderService.fetchOrder(billRequestDto);

        return new ApiResponse();
    }
}
