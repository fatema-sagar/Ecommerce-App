package com.ecommerce.ecommApp.customers.repository;

import com.ecommerce.ecommApp.customers.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
