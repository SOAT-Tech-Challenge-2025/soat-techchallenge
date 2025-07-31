package com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface OrderAdaptersGetRepository extends JpaRepository<JPAOrderEntity,String> {
    @Query(value = "SELECT generate_order_id()", nativeQuery = true)
    String getByOrderId();
}
