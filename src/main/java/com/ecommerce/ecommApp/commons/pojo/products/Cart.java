package com.ecommerce.ecommApp.commons.pojo.products;

import com.ecommerce.ecommApp.products.composite.CartIdentity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity


@Table(name = "cart")
@Data
public class Cart {
//    @Id
//    @Column(name = "cart_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long cart_id;


    @EmbeddedId
    private CartIdentity cartIdentity;

    @JsonProperty("quantity")
    @Column(name = "quantity")
    int quantity;


//    @JsonProperty("customerId")
//    @Column(name = "customer_id")
//    private long customerId;
//
//    @JsonProperty("productId")
//    @NotNull
//    @Column(name = "product_id")
//    private Long productId;


}
