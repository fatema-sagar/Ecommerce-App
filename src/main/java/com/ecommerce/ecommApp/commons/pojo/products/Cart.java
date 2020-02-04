package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Cart {

  @JsonProperty
  @Id @Getter @NotNull
  private long customer_id;

  @JsonProperty @NotNull
  private long product_id;

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
          },
          mappedBy = "cart")
  private Set<Inventory> inventories = new HashSet<>();

}
