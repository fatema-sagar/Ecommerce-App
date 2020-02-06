package com.ecommerce.ecommApp.orders.Models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Data
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "order_id")
    private String orderID;

    @Column(name = "customer_id")
    private Long customerID;

    @Column(name = "product_id")
    private Long productID;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_cost")
    private Integer totalCost;

    @Column(name = "order_status")
    private String orderStatus;

}
