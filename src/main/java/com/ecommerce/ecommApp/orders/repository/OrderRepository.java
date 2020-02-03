package com.ecommerce.ecommApp.orders.repository;

import com.ecommerce.ecommApp.orders.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query(value="Select * from order where customerID=:customerID",nativeQuery = true)
    List<Orders> allOrdersofCustomer(@Param("customerID") Long customerID);

    @Query(value="Update order set status=:status where customerID=:customerID",nativeQuery = true)
    List<Orders> updateOrderStatus(@Param("status") String status,@Param("customerID") Long customerID);
}
