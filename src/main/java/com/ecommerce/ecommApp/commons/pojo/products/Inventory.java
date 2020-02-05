package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "inventory")
//@Getter
//@Setter
@NoArgsConstructor
public class Inventory {

  @Id @Column(unique = true)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  long inventoryid;

  public enum Size {XS, S, M, L, XL};
   @Column(name = "size")
  Size size;

  @Column(name = "quantity")
  int quantity;

  @Column(name = "price")
  float price;




  public long getInventoryid() {
    return inventoryid;
  }

  public void setInventoryid(long inventoryid) {
    this.inventoryid = inventoryid;
  }

  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  @ManyToMany(fetch = FetchType.LAZY,
          cascade = {
                  CascadeType.PERSIST,
                  CascadeType.MERGE
          },
          mappedBy = "inventory")
  private Set<Cart> cart = new HashSet<>();

  public Set<Cart> getCart() {
    return cart;
  }

  public void setCart(Set<Cart> cart) {
    this.cart = cart;
  }

//  public Inventory(){
//
//  }

}
