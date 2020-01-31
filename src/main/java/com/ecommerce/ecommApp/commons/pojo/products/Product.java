package com.ecommerce.ecommApp.commons.pojo.products;

import lombok.Getter;

import javax.persistence.*;

@Table
public class Product {
  @Getter @Id @Column(unique = true)
  long ProductID;

  public enum Gender {MALE, FEMALE};

  @Getter @Column
  Gender gender;

  @Getter
  String type;

  @Getter
  int InventoryID;

  @Getter
  float price;
}
