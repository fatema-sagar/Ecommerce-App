package com.ecommerce.ecommApp.customers.utils;

import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
import com.ecommerce.ecommApp.customers.models.Customer;

public class CustomerUtil {

    public CustomerDto convertToPojo(Customer customer) {
        CustomerDto customerDto=new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setGender(customer.getGender());
        customerDto.setId(customer.getId());
        customerDto.setNumber(customer.getNumber());
        customerDto.setWhatsapp(customer.getWhatsapp());
        return customerDto;
    }
}
