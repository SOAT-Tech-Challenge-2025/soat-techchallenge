package com.store.soattechchallenge.order.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.order.domain.model.Order;
import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.OrderGetResponseDTO;
import com.store.soattechchallenge.order.infrastructure.adapters.out.entity.JPAOrderEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OrderMapper {

    JPAOrderEntity toJPAOrderEntity(Order order);
    Page<OrderGetResponseDTO> toOrderGetResponseDTO(Page<JPAOrderEntity> jpaOrderEntityPage);
    Optional<OrderGetResponseDTO> toOrderGetResponseDTO(Optional<JPAOrderEntity> jpaOrderEntity);

}
