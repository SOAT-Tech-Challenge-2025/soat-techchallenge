package com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers;

import com.store.soattechchallenge.shoppingCart.order.domain.entities.Order;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.JPAOrderEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OrderMapper {

    JPAOrderEntity toJPAOrderEntity(Order order);
    Page<OrderResponseDTO> toOrderGetResponseDTO(Page<Order> orders);
    Optional<OrderResponseDTO> toOrderGetResponseDTO(Optional<Order> order);
    Optional<Order> entityToOrder(Optional<JPAOrderEntity> jpaOrderEntity);
    Page<Order> entitiesToOrders(Page<JPAOrderEntity> jpaOrderEntities);
}
