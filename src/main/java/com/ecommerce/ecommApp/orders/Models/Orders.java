package com.ecommerce.ecommApp.orders.Models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.UUID;

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
    @Column
    private String orderID;
    private Long customerID;
    private Long productID;
    private Integer quantity;
    private Integer cost;
    private String status;
}
