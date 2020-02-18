package com.ecommerce.ecommApp.billGenerator.service;

import com.ecommerce.ecommApp.view.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public class BillGeneratorService {

    public ApiResponse generateBill() {
        return new ApiResponse();
    }
}
