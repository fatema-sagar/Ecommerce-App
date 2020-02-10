package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
public class Inventory {

  @Id @Column(unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long inventoryId;

  @Column
  private String size;

  @Column
  private int quantity;

  @Column
  private float price;

  @JsonProperty @NotNull
  @Column
  private String category;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "productId", nullable = false)
  private Product product;
}
