package com.store.soattechchallenge.shoppingCart.order.presenters;

import com.store.soattechchallenge.shoppingCart.order.domain.entities.Order;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.JPAOrderEntity;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.OrderMapper;
import org.springframework.data.domain.Page;

import java.util.Optional;

public class OrderHttpPresenter {
    private final OrderMapper orderMapper;

    public OrderHttpPresenter(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public Optional<OrderResponseDTO> execute(Optional<Order>order){
        return orderMapper.toOrderGetResponseDTO(order);
    }

    public Page<OrderResponseDTO> execute(Page<Order> orders) {
        return orderMapper.toOrderGetResponseDTO(orders);
    }


}
