package com.store.soattechchallenge.order.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.order.domain.model.Order;
import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.OrderResponseDTO;
import com.store.soattechchallenge.order.infrastructure.adapters.out.entity.JPAOrderEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OrderMapper {

    JPAOrderEntity toJPAOrderEntity(Order order);
    Page<OrderResponseDTO> toOrderGetResponseDTO(Page<JPAOrderEntity> jpaOrderEntityPage);
    Optional<OrderResponseDTO> toOrderGetResponseDTO(Optional<JPAOrderEntity> jpaOrderEntity);

}
