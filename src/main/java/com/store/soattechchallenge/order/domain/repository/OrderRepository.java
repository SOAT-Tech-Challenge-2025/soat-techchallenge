package com.store.soattechchallenge.order.domain.repository;

import com.store.soattechchallenge.order.domain.model.Order;
import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.OrderGetResponseDTO;
import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.OrderPostUpResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderRepository {

    Optional<OrderGetResponseDTO> findById(Long id);
    Page<OrderGetResponseDTO> findAll(Pageable pageable);
    void save(Order Order);
    OrderPostUpResponseDTO update(Order Order, Long id);
    OrderPostUpResponseDTO deleteById(Long id);

}
