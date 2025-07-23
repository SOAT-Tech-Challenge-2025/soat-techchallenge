package com.store.soattechchallenge.shoppingCart.order.infrastructure.gateways;

import com.store.soattechchallenge.shoppingCart.order.domain.entities.Order;
import com.store.soattechchallenge.shoppingCart.order.gateways.OrderRepositoryGateways;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.JPAOrderEntity;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.OrderMapper;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.OrderAdaptersGetRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

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
    public Page<Order> findAll(Pageable pageable) {
        Page<JPAOrderEntity> jpaOrderEntities = repository.findAll(pageable);
        return this.mapper.entitiesToOrders(jpaOrderEntities);
    }


    @Override
    public Optional<Order> findOrderById(String orderId) {
        Optional<JPAOrderEntity> jpaOrderEntity = repository.findByIdWithProdutos(orderId);
        return this.mapper.entityToOrder(jpaOrderEntity);
    }

    @Override
    public String orderId() {
        return repository.getByOrderId();
    }
}
