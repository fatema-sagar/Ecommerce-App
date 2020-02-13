package com.ecommerce.ecommApp.commons.pojo.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrdersDTO {

    @JsonProperty("order_id")
    private String orderID;

    @JsonProperty("customer_id")
    private long customerID;

    @JsonProperty("product_id")
    private long productID;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("total_cost")
    private float cost;

    @JsonProperty("order_status")
    private String status;
}
