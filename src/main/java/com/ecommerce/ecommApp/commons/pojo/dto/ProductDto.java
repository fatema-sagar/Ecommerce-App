package com.ecommerce.ecommApp.commons.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @JsonProperty("product_id")
    private long productId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("category")
    private String category;

    @JsonProperty("product_name")
    private String name;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("product_description")
    private String productDescription;

    @JsonProperty("size")
    private String size;

    @JsonProperty("price")
    private float price;

    @JsonProperty("product_image")
    private String image;

}
