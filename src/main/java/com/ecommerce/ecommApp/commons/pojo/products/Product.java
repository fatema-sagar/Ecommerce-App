package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.*;

@Table(name = "Product")
public class Product {

  @JsonProperty
  @Getter @Id @Column(unique = true) @ManyToOne
  long ProductID;

  public enum Gender {MALE, FEMALE};

  @JsonProperty
  @Getter @Column
  Gender gender;

  @Getter @Column
  String category;

  @Getter @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventoryID")
  long InventoryID;

}
