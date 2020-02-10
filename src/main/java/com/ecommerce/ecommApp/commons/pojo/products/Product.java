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
  private long productId;

  @JsonProperty @NotNull
  @Column
  private String productName;

  @JsonProperty
  private String productDescription;

  @JsonProperty
  private String image;

  @ManyToMany(fetch = FetchType.EAGER,
          cascade = {
                  CascadeType.PERSIST,
                  CascadeType.MERGE
          })
  @JoinTable(name = "product_cart",
          joinColumns = { @JoinColumn(name = "productId") },
          inverseJoinColumns = { @JoinColumn(name = "cart_id") })

  private Set<Cart> cart = new HashSet<>();

}
