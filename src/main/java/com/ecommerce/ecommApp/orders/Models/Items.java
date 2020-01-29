package com.ecommerce.ecommApp.orders.Models;


import lombok.Data;

@Data
public class Items {
    private long productID;
    private long quantity;
    private long cost;
}
