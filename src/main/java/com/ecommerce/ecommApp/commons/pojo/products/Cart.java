package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cart")
//@Getter
//@Setter
@NoArgsConstructor
public class Cart {

  @Id
  @Column(name = "cart_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cart_id;


//  @JsonProperty
//  @Id @Getter @NotNull
//  private long customer_id;
//
//  @JsonProperty @NotNull
//  private long product_id;

  @JsonProperty @NotNull
  @Column(name = "quantity")
  int quantity;

  @JsonProperty
  @Column(name = "Availability")
  Boolean availability;


@ManyToMany(fetch = FetchType.LAZY,
        cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
        })
@JoinTable(name = "cart_inventory",
        joinColumns = { @JoinColumn(name = "cart_id") },
        inverseJoinColumns = { @JoinColumn(name = "inventoryid") })
private Set<Inventory> inventory = new HashSet<>();


  public Long getCart_id() {
    return cart_id;
  }

  public void setCart_id(Long cart_id) {
    this.cart_id = cart_id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Boolean getAvailability() {
    return availability;
  }

  public void setAvailability(Boolean availability) {
    this.availability = availability;
  }

  public Set<Inventory> getInventory() {
    return inventory;
  }

  public void setInventory(Set<Inventory> inventory) {
    this.inventory = inventory;
  }



}
