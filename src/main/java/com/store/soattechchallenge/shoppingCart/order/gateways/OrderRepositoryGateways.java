package com.store.soattechchallenge.shoppingCart.order.gateways;

import com.store.soattechchallenge.shoppingCart.order.domain.entities.Order;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;

import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.JPAOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderRepositoryGateways {

    void save(Order Order);
    Optional<JPAOrderEntity>  findOrderById(String orderId);
    Page<JPAOrderEntity> findAll(Pageable pageable);
    String orderId();
}
