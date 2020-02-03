package com.ecommerce.ecommApp.orders.Models;

import com.ecommerce.ecommApp.orders.enums.OrderStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
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

}
