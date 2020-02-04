package com.ecommerce.ecommApp.commons.pojo.orders;

import com.ecommerce.ecommApp.commons.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class OrdersDTO {

    @JsonProperty("orderID")
    private UUID orderID;

    @JsonProperty("customerID")
    private long customerID;

    @JsonProperty("productID")
    private long productID;

    @JsonProperty("quantity")
    private long quantity;

    @JsonProperty("cost")
    private long cost;

    @JsonProperty("status")
    private OrderStatus status;
}
