package com.ecommerce.ecommApp.orders.Models;

import com.ecommerce.ecommApp.commons.enums.OrderStatus;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.UUID;
@Component
@Entity
@Data
@Table(name ="orders")
public class Orders {
    @Id
//    @GeneratedValue(generator ="UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator")
    @Column
    private UUID orderID;
    private Long customerID;
    private Long productID;
    private Integer quantity;
    private Integer cost;
    @Enumerated
    private OrderStatus status;

}
