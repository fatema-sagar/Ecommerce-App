package com.ecommerce.ecommApp.products.repositories;

import com.ecommerce.ecommApp.commons.pojo.products.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
}
