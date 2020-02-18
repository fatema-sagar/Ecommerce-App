package com.ecommerce.ecommApp.view.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
public class ViewProductDto {

    @NonNull
    @JsonProperty("customer_id")
    private String customerId;

    @NonNull
    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("category")
    private String category;

    @JsonProperty("name")
    private String name;

    @JsonProperty("brand")
    private String brand;

}
