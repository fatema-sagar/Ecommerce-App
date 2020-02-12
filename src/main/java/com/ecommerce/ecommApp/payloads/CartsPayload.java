package com.ecommerce.ecommApp.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CartsPayload {

    private Long productId;
    private Integer quantity;
    private Long customerId;

//    public CartsPayload(Long pid, Integer quantity, ) {
//        this.pid = pid;
//        this.quantity = quantity;
//    }
}
