package com.ecommerce.ecommApp.commons.pojo.notification;

import com.ecommerce.ecommApp.commons.pojo.customer.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderPlaced
{
    @JsonProperty("mode")
    private List<String> mode;

    @JsonProperty("order_id")
    private String OrderID;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("quandity")
    private Integer quandity;

    @JsonProperty("total_cost")
    private Integer totalCost;

    @JsonProperty("customer")
    private Customer customer;

    public String toString()
    {
        return String.format("order %s placed for %s %s with Customer Id %s ",getOrderID(),getQuandity(),getProductName(),getCustomer().getId());
    }
}

// example  :   {"mode":["Text_SMS","EMAIL"],"order_id":"ovq1","product_name":"ABC Product","quandity":3,"total_cost":3000,"customer":{"id":"ox1","name":"abc","number":8851530831,"email":"sagarbindal992@gmail.com","whatsapp":1234567890}}