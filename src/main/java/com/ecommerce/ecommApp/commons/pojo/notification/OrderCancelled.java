package com.ecommerce.ecommApp.commons.pojo.notification;

import com.ecommerce.ecommApp.commons.pojo.customer.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderCancelled
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
        return String.format("order %s for %s %s with Customer Id %s is successfully cancelled ",getOrderID(),getQuandity(),getProductName(),getCustomer().getId());
    }

}
