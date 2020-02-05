package com.ecommerce.ecommApp.commons.pojo.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemsDTO {
    @JsonProperty("productID")
    private long productID;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("cost")
    private int cost;
}
