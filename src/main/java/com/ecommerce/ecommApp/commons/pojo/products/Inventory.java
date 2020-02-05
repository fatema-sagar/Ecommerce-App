package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table
public class Inventory {

  @Id @Column(unique = true)
  @Getter @GeneratedValue(strategy = GenerationType.SEQUENCE)
  int inventoryid;

  public enum Size {XS, S, M, L, XL};
  @Getter @Column
  Size size;

  @Getter @Column
  int quantity;

  @Getter @Column
  float price;
}
