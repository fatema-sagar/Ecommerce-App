package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table
public class Inventory {

  @ManyToOne @Id @Column(unique = true)
  long inventoryID;

  public enum Size {XS, S, M, L, XL};
  @Getter @Column
  Size size;

  @Getter @Column
  int quantity;

  @Getter @Column
  float price;
}
