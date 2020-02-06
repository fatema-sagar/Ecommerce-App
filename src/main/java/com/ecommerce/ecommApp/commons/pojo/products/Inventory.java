package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table
@Data
public class Inventory {

  @Id @Column(unique = true)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long inventoryid;

  @Column
  private String size;

  @Column
  private int quantity;

  @Column
  private float price;
}
