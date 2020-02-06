package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import org.apache.kafka.common.protocol.types.Field;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Product")
@Data
public class Product {

  @JsonProperty
  @Id @Column(unique = true) @GeneratedValue(strategy = GenerationType.AUTO)
  private long productid;

  @JsonProperty @NotNull
  @Column
  private String gender;

  @JsonProperty @NotNull
  @Column
  private String category;

  @JsonProperty
  @NotNull
  private long inventoryid;

}
