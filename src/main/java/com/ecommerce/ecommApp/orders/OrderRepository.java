package com.ecommerce.ecommApp.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID> {
    @Query(value="Select * from order where customerID=:customerID",nativeQuery = true)
    List<Orders> allOrdersofCustomer(@Param("customerID") Integer customerID);

    @Query(value="Update order set status=:status where customerID=:customerID",nativeQuery = true)
    List<Orders> updateOrderStatus(@Param("status") String status,@Param("customerID") Integer customerID);
}
