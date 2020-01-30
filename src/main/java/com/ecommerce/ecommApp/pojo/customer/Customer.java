package com.ecommerce.ecommApp.pojo.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Customer {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("email")
    private String email;
    @JsonProperty("whatsapp")
    private Integer whatsapp;

}