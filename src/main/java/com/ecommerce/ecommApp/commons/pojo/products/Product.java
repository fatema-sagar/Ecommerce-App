package com.ecommerce.ecommApp.commons.pojo.products;

import lombok.Getter;

import javax.persistence.*;

@Table
public class Product {
  @Getter @Id @Column(unique = true) @ManyToOne
  long ProductID;

  public enum Gender {MALE, FEMALE};

  @Getter @Column
  Gender gender;

  @Getter @Column
  String type;

  @Getter @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventoryID")
  long InventoryID;

  @Getter @Column(updatable = false)
  float price;
}
