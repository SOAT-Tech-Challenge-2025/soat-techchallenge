package com.store.soattechchallenge.order.infrastructure.adapters.out.repository;

import com.store.soattechchallenge.order.infrastructure.adapters.out.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAdaptersGetRepository extends JpaRepository<OrderEntity,Long> {
}
