package com.ecommerce.ecommApp.commons.pojo.notification;

import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserRegistered {
    @JsonProperty("mode")
    private List<String> mode;
    @JsonProperty("customerDto")
    private CustomerDto customerDto;

    public String toString()
    {
        return mode+" : "+ customerDto.getName();
    }

}
