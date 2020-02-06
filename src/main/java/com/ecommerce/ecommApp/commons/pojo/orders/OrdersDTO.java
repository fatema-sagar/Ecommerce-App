package com.ecommerce.ecommApp.commons.pojo.orders;

import com.ecommerce.ecommApp.commons.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class OrdersDTO {

    @JsonProperty("order_id")
    private UUID orderID;

    @JsonProperty("customer_id")
    private long customerID;

    @JsonProperty("product_id")
    private long productID;

    @JsonProperty("quandity")
    private int quantity;

    @JsonProperty("total_cost")
    private int cost;

    @JsonProperty("status")
    private OrderStatus status;
}
