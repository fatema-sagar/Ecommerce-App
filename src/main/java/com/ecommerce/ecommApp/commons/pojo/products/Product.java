package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

  @JsonProperty
 @Id @Column(unique = true) @GeneratedValue(strategy = GenerationType.IDENTITY)
  long productid;

  public enum Gender {MALE, FEMALE};

  @JsonProperty @NotNull
  @Column
  Gender gender;

  @JsonProperty @NotNull
  @Column
  String category;

  @JsonProperty
//  @Getter
  @NotNull
  long inventoryid;

  @ManyToMany(fetch = FetchType.LAZY,
          cascade = {
                  CascadeType.PERSIST,
                  CascadeType.MERGE
          })


  @JoinTable(name = "product_cart",
          joinColumns = { @JoinColumn(name = "productid") },
          inverseJoinColumns = { @JoinColumn(name = "cart_id") })

  private Set<Cart> cart = new HashSet<>();



}
