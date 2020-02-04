package com.ecommerce.ecommApp.customers.utils;

import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.customers.models.Customer;

public class CustomerUtil {

    public CustomerDto convertToPojo(Customer customer) {
        CustomerDto.CustomerDtoBuilder builder = CustomerDto.builder();
        builder.email(customer.getEmail())
                .number(customer.getNumber())
                .id(customer.getId())
                .name(customer.getName())
                .whatsapp(customer.getWhatsapp())
                .gender(customer.getGender());
        return builder.build();
    }
}
