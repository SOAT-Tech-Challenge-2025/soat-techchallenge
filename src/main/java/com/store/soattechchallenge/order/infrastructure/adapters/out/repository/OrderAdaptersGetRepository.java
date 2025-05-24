package com.store.soattechchallenge.order.infrastructure.adapters.out.repository;

import com.store.soattechchallenge.order.infrastructure.adapters.out.entity.JPAOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAdaptersGetRepository extends JpaRepository<JPAOrderEntity,String> {
    @Query(value = "SELECT generate_order_id()", nativeQuery = true)
    String getByOrderId();
}
