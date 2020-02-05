package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Product")
public class Product {

  @JsonProperty
  @Getter @Id @Column(unique = true) @GeneratedValue(strategy = GenerationType.AUTO)
  int productid;

  public enum Gender {MALE, FEMALE};

  @JsonProperty @NotNull
  @Getter @Column
  Gender gender;

  @JsonProperty @NotNull
  @Getter @Column
  String category;

  @JsonProperty
  @Getter @NotNull
  int inventoryid;

}
