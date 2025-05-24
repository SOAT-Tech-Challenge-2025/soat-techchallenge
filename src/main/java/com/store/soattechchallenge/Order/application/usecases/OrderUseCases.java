package com.store.soattechchallenge.Order.application.usecases;

import com.store.soattechchallenge.Order.infrastructure.adapters.in.dto.OrderGetResponseDTO;
import com.store.soattechchallenge.Order.infrastructure.adapters.in.dto.OrderRequestDTO;
import com.store.soattechchallenge.Order.infrastructure.adapters.in.dto.OrderPostResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderUseCases {

    OrderPostResponseDTO saveOrder(OrderRequestDTO Product);
    Page<OrderGetResponseDTO> getAllOrders(Pageable pageable);
    Optional<OrderGetResponseDTO> getOrdeById(String id);
}
