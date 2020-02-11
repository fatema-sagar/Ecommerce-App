package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity


@Table(name = "cart")
@Data
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cart_id;

    @JsonProperty
    @Column(name = "quantity")
    int quantity;

    @JsonProperty
    @Column(name = "availability")
    Boolean availability;


    @JsonProperty
    @Column(name = "customer_id")
    private long customerId;

    @JsonProperty("productId")
    @NotNull
    @Column(name = "product_id")
    private Long productId;


}
