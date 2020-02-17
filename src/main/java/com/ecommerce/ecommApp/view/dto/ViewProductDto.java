package com.ecommerce.ecommApp.view.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
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
