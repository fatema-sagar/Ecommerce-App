package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
@Data
public class Product {

  @JsonProperty("product_id")
  @Id
  @Column(unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long productid;

  @JsonProperty("category")
  @NotNull
  @Column
  private String category;

  @JsonProperty("product_description")
  @Column
  private String productDescription;

  @JsonProperty("size")
  @Column
  private String size;

  @JsonProperty("quantity")
  @Column
  private int quantity;

  @JsonProperty("price")
  @Column
  private float price;

}


//  @ManyToMany(fetch = FetchType.EAGER,
//          cascade = {
//                  CascadeType.PERSIST,
//                  CascadeType.MERGE
//          })
//  @JoinTable(name = "product_cart",
//          joinColumns = { @JoinColumn(name = "productid") },
//          inverseJoinColumns = { @JoinColumn(name = "cart_id") })

//  private Set<Cart> cart = new HashSet<>();
