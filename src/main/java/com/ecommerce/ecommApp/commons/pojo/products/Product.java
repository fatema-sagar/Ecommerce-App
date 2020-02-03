package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "Product")
public class Product {

  @JsonProperty
  @Getter @Id @Column(unique = true) @GeneratedValue(strategy = GenerationType.AUTO)
  long ProductID;

  public enum Gender {MALE, FEMALE};

  @JsonProperty
  @Getter @Column
  Gender gender;

  @JsonProperty
  @Getter @Column
  String category;

  @JsonProperty
  @Getter
  long InventoryID;

}
