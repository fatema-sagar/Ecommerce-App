package com.ecommerce.ecommApp.billGenerator.service;

import com.ecommerce.ecommApp.billGenerator.dto.BillRequestDto;
import com.ecommerce.ecommApp.commons.pojo.orders.OrdersDTO;
import com.ecommerce.ecommApp.view.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BillGeneratorService {

    @Autowired
    private FetchOrderService fetchOrderService;


    public ApiResponse billGenerate(BillRequestDto billRequestDto) {

        List<OrdersDTO> ordersList = fetchOrderService.fetchOrder(billRequestDto);

        return new ApiResponse(HttpStatus.OK, "Successfully get orders", ordersList);
    }
}
