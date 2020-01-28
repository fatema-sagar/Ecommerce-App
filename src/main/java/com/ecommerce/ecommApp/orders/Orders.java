package com.ecommerce.ecommApp.orders;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name ="order")
public class Orders {
    @Id
    @GeneratedValue(generator ="UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false,unique = true)
    private UUID orderID;
    private long customerID;
    private long productID;
    private long quantity;
    private long cost;
    @Enumerated
    private OrderStatus status;

    public UUID getOrderID() {
        return orderID;
    }
//    public void setOrderID(UUID orderID) {
//        this.orderID = orderID;
//    }

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
