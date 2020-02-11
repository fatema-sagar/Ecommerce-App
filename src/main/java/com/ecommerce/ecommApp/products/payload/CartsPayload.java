package com.ecommerce.ecommApp.products.payload;
import lombok.Data;

@Data
public class CartsPayload {

    private Long productId;
    private Integer quantity;
    private Long customerId;

}

