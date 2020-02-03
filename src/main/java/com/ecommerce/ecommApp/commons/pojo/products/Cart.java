package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Cart {

  @JsonProperty
  @Id @Getter
  private long customer_id;

  @JsonProperty
  private long product_id;

  @JsonProperty
  @Column(name = "quantity")
  int quantity;
}
