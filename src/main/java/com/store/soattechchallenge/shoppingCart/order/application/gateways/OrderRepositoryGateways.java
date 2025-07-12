package com.store.soattechchallenge.shoppingCart.order.application.gateways;

import com.store.soattechchallenge.shoppingCart.order.domain.entities.Order;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderRepositoryGateways {

    void save(Order Order);
    Optional<OrderResponseDTO>  findOrderById(String orderId);
    Page<OrderResponseDTO> findAll(Pageable pageable);
    String orderId();
}
