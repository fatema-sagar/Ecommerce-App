package com.ecommerce.ecommApp.orders.Models;

import com.ecommerce.ecommApp.commons.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
    @Column(updatable = false, nullable = false,unique = true)
    private UUID orderID;
    private Long customerID;
    private Long productID;
    private Integer quantity;
    private Integer cost;
    @Enumerated
    private OrderStatus status;

    public Orders(Long cId, Long pId, Integer quant, Integer cost, OrderStatus orderStatus)
    {
        super();
        this.orderID=UUID.randomUUID();
        this.customerID=cId;
        this.productID=pId;
        this.quantity=quant;
        this.cost=cost;
        this.status=orderStatus;
    }

}
