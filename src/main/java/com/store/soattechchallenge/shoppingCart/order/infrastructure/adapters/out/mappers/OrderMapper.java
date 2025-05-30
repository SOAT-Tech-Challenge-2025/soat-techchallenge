package com.store.soattechchallenge.shoppingCart.order.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.shoppingCart.order.domain.model.Order;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.adapters.in.dto.OrderResponseDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.adapters.out.model.JPAOrderEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OrderMapper {

    JPAOrderEntity toJPAOrderEntity(Order order);
    Page<OrderResponseDTO> toOrderGetResponseDTO(Page<JPAOrderEntity> jpaOrderEntityPage);
    Optional<OrderResponseDTO> toOrderGetResponseDTO(Optional<JPAOrderEntity> jpaOrderEntity);

}
