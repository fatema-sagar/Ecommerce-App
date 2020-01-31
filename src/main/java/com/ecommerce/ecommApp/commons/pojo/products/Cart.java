package com.ecommerce.ecommApp.commons.pojo.products;

import javax.persistence.*;

@Table
public class Cart {

  @Id @Column(unique = true)
  long cart_id;

  @OneToMany(fetch = FetchType.LAZY, targetEntity = Product.class, mappedBy = "ProductID")
  long product_id;

}
