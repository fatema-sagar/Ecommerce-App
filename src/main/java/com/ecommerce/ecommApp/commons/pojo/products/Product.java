package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
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

//  @JsonProperty
////  @Getter
//  @NotNull
//  long inventoryid;

  @ManyToMany(fetch = FetchType.LAZY,
          cascade = {
                  CascadeType.PERSIST,
                  CascadeType.MERGE
          }
//          ,
//          mappedBy = "product"
  )

  @JoinTable(name = "product_cart",
          joinColumns = { @JoinColumn(name = "productid") },
          inverseJoinColumns = { @JoinColumn(name = "cart_id") })
//  private Set<Product> product = new HashSet<>();
  private Set<Cart> cart = new HashSet<>();

  public Set<Cart> getCart() {
    return cart;
  }

  public void setCart(Set<Cart> cart) {
    this.cart = cart;
  }

  public long getProductid() {
    return productid;
  }

  public void setProductid(long productid) {
    this.productid = productid;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

//  public long getInventoryid() {
//    return inventoryid;
//  }
//
//  public void setInventoryid(long inventoryid) {
//    this.inventoryid = inventoryid;
//  }
}
