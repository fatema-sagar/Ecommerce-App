package com.ecommerce.ecommApp.orders.Models;


import lombok.Data;

@Data
public class Items {
    private Long productID;
    private Integer quantity;
    private Integer cost;
}
