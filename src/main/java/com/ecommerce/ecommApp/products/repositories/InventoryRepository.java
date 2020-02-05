package com.ecommerce.ecommApp.products.repositories;

import com.ecommerce.ecommApp.commons.pojo.products.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
