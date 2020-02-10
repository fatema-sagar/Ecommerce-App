package com.ecommerce.ecommApp.commons.pojo.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
public class Cart {

  @Id
  @Column(name = "cart_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cart_id;

  @JsonProperty
  @NotNull
  private long customer_id;

  @JsonProperty @NotNull
  private long product_id;

  @JsonProperty @NotNull
  @Column(name = "quantity")
  private int quantity;

  @JsonProperty
  @Column(name = "Availability")
  Boolean availability;


  @ManyToMany(fetch = FetchType.EAGER,
        cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
        },
          mappedBy = "cart")

  private Set<Product> product = new HashSet<>();

}
