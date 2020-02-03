package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Table
public class Cart {

  @JsonProperty
  private long customer_id;

  @JsonProperty
  @OneToMany(fetch = FetchType.EAGER, targetEntity = Product.class, mappedBy = "ProductID")
  private List<Product> products;

  @JsonProperty
  @Column(name = "quantity")
  int quantity;

  @JsonProperty
  @Column(name = "Availability")
  Boolean availability;

}
