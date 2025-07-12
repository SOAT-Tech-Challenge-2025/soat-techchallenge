package com.store.soattechchallenge.shoppingCart.order.infrastructure.gateways;

import com.store.soattechchallenge.shoppingCart.order.domain.entities.Order;
import com.store.soattechchallenge.shoppingCart.order.application.gateways.OrderRepositoryGateways;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.OrderMapper;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.OrderAdaptersGetRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Component
 public class OrderRepositoryGatewaysImpl implements OrderRepositoryGateways {

    private final OrderAdaptersGetRepository repository;
    public final OrderMapper mapper;

    public OrderRepositoryGatewaysImpl(OrderAdaptersGetRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public void save(Order order) {
        repository.save(mapper.toJPAOrderEntity(order));
    }

    @Override
    public Page<OrderResponseDTO> findAll(Pageable pageable) {
        return mapper.toOrderGetResponseDTO(repository.findAll(pageable));
    }


    @Override
    public Optional<OrderResponseDTO> findOrderById(String orderId) {
        return mapper.toOrderGetResponseDTO(repository.findById(orderId));
    }

    @Override
    public String orderId() {
        return repository.getByOrderId();
    }
}
