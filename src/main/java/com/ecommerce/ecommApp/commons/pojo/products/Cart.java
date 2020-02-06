package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table
@Data
public class Cart {

  @JsonProperty
  @Id @NotNull
  private long customer_id;

  @JsonProperty @NotNull
  private long product_id;

  @JsonProperty @NotNull
  @Column(name = "quantity")
  private int quantity;
}
