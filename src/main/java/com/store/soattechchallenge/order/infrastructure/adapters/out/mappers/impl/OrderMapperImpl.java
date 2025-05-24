package com.store.soattechchallenge.order.infrastructure.adapters.out.mappers.impl;

import com.store.soattechchallenge.order.domain.model.Order;
import com.store.soattechchallenge.order.infrastructure.adapters.in.dto.OrderGetResponseDTO;
import com.store.soattechchallenge.order.infrastructure.adapters.out.entity.OrderEntity;
import com.store.soattechchallenge.order.infrastructure.adapters.out.mappers.OrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class OrderMapperImpl implements OrderMapper {

    public Page<OrderGetResponseDTO> modelToProductGetResponseDTO(Page<OrderEntity> products) {
        return products.map(product -> {
            OrderGetResponseDTO orderGetResponseDTO = new OrderGetResponseDTO();
            orderGetResponseDTO.setId(product.getId());
            orderGetResponseDTO.setNameProduct(product.getProductName());
            orderGetResponseDTO.setIdCategory(product.getIdCategory());
            orderGetResponseDTO.setUnitPrice(product.getUnitPrice().doubleValue());
            orderGetResponseDTO.setPreparationTime(product.getPreparationTime().longValue());
            return orderGetResponseDTO;
        });
    }

    public Optional<OrderGetResponseDTO> modelToProductGetResponseDTO(Optional<OrderEntity> product) {
        return product.map(p -> {
            OrderGetResponseDTO orderGetResponseDTO = new OrderGetResponseDTO();
            orderGetResponseDTO.setId(p.getId());
            orderGetResponseDTO.setNameProduct(p.getProductName());
            orderGetResponseDTO.setIdCategory(p.getIdCategory());
            orderGetResponseDTO.setUnitPrice(p.getUnitPrice().doubleValue());
            orderGetResponseDTO.setPreparationTime(p.getPreparationTime().longValue());
            return orderGetResponseDTO;
        });
    }

    public OrderEntity productToProductUpdateMap(Order order, Long id){

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(id);
        orderEntity.setProductName(order.getProductName());
        orderEntity.setIdCategory(order.getIdCategory());
        orderEntity.setUnitPrice(BigDecimal.valueOf(order.getUnitValue()));
        orderEntity.setPreparationTime(order.getPreparationTime().intValue());
        orderEntity.setInclusionDate(order.getDateInclusion());
        orderEntity.setTimestamp(order.getTimestamp());
        return orderEntity;
    }

    public OrderEntity productToProductEntityMap(Order order){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setProductName(order.getProductName());
        orderEntity.setIdCategory(order.getIdCategory());
        orderEntity.setUnitPrice(BigDecimal.valueOf(order.getUnitValue()));
        orderEntity.setPreparationTime(order.getPreparationTime().intValue());
        orderEntity.setInclusionDate(order.getDateInclusion());
        orderEntity.setTimestamp(order.getTimestamp());
        return orderEntity;
    }

}
