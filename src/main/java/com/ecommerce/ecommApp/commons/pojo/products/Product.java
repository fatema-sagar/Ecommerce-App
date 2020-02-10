package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
@Data
public class Product {

  @JsonProperty
  @Id @Column(unique = true) @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long productid;

  @JsonProperty @NotNull
  @Column
  private String category;

  @Column
  private String productDescription;

  @Column
  private String size;

  @Column
  private int quantity;

  @Column
  private float price;

  @ManyToMany(fetch = FetchType.EAGER,
          cascade = {
                  CascadeType.PERSIST,
                  CascadeType.MERGE
          })
  @JoinTable(name = "product_cart",
          joinColumns = { @JoinColumn(name = "productid") },
          inverseJoinColumns = { @JoinColumn(name = "cart_id") })

  private Set<Cart> cart = new HashSet<>();

}
