package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Inventory {

  @Id @Column(unique = true)
  @Getter @GeneratedValue(strategy = GenerationType.SEQUENCE)
  long inventoryid;

  public enum Size {XS, S, M, L, XL};
  @Getter @Column
  Size size;

  @Getter @Column
  int quantity;

  @Getter @Column
  float price;


  @ManyToMany(fetch = FetchType.LAZY,
          cascade = {
                  CascadeType.PERSIST,
                  CascadeType.MERGE
          })
  @JoinTable(name = "inventory_cart",
          joinColumns = { @JoinColumn(name = "inventoryid") },
          inverseJoinColumns = { @JoinColumn(name = "customer_id") })
  private Set<Cart> carts = new HashSet<>();
}
