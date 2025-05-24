package com.store.soattechchallenge.Order.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.Order.domain.model.Order;
import com.store.soattechchallenge.Order.infrastructure.adapters.in.dto.OrderGetResponseDTO;
import com.store.soattechchallenge.Order.infrastructure.adapters.out.entity.JPAOrderEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OrderMapper {

    JPAOrderEntity toJPAOrderEntity(Order order);
    Page<OrderGetResponseDTO> toOrderGetResponseDTO(Page<JPAOrderEntity> jpaOrderEntityPage);
    Optional<OrderGetResponseDTO> toOrderGetResponseDTO(Optional<JPAOrderEntity> jpaOrderEntity);

}
