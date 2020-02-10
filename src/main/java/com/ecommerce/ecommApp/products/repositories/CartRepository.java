package com.ecommerce.ecommApp.products.repositories;

import com.ecommerce.ecommApp.commons.pojo.products.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
@Query(nativeQuery = true, value = "select * from cart where cart_id=?1")
Optional<Cart> findByCartId(Long cartId);

}
