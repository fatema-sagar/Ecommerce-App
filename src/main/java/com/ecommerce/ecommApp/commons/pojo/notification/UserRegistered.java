package com.ecommerce.ecommApp.commons.pojo.notification;

import com.ecommerce.ecommApp.commons.pojo.customer.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserRegistered {
    @JsonProperty("mode")
    private List<String> mode;
    @JsonProperty("customer")
    private Customer customer;

    public String toString()
    {
        return mode+" : "+customer.getName();
    }

}
