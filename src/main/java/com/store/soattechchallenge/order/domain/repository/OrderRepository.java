package com.store.soattechchallenge.order.domain.repository;

import com.store.soattechchallenge.order.domain.model.Order;
import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.OrderGetResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderRepository {

    void save(Order Order);
    Optional<OrderGetResponseDTO>  findOrderById(String orderId);
    Page<OrderGetResponseDTO> findAll(Pageable pageable);
    String orderId();
}
