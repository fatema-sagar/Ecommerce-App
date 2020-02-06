package com.ecommerce.ecommApp.commons.pojo.notification;

import com.ecommerce.ecommApp.commons.pojo.customer.CustomerDto;
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

    @JsonProperty("customerDto")
    private CustomerDto customerDto;

    public String toString()
    {
        return String.format("order %s for %s %s with CustomerDto Id %s is successfully cancelled ",getOrderID(),getQuandity(),getProductName(), getCustomerDto().getId());
    }

}
