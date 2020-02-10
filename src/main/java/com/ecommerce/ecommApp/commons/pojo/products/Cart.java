package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity

@Table(name = "cart")
@Data
public class Cart {

    @JsonProperty
    @NotNull
    @Column(name = "quantity")
    int quantity;
    @JsonProperty
    @Column(name = "availability")
    Boolean availability;
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cart_id;
    @JsonProperty
    @NotNull
    @Column(name = "customer_id")
    private long customerId;
    @JsonProperty
    @NotNull
    private long product_id;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "cart")

    private Set<Product> product = new HashSet<>();



}
