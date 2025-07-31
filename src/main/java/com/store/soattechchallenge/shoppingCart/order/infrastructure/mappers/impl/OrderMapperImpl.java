package com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.impl;

import com.store.soattechchallenge.shoppingCart.order.domain.entities.Order;
import com.store.soattechchallenge.shoppingCart.order.domain.entities.OrderProduct;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.ProductResponse;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.JPAOrderEntity;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.JPAOrderProductEntity;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.JPAOrderProductId;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.OrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderMapperImpl implements OrderMapper {


    @Override
    public JPAOrderEntity toJPAOrderEntity(Order order) {
        List<JPAOrderProductEntity> jpaOrderProductList = new ArrayList<>();
        JPAOrderEntity orderEntity = new JPAOrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setTotalAmountOrder(order.getTotalAmountOrder());
        orderEntity.setMinute(order.getMinute());
        orderEntity.setClientId(order.getClientId());
        orderEntity.setTimestamp(order.getTimestamp());
        for (OrderProduct p : order.getOrderProducts()) {
            JPAOrderProductEntity jpaOrderProductEntity = new JPAOrderProductEntity();
            JPAOrderProductId JPAOrderProductId = new JPAOrderProductId(order.getId(), p.getProductId());
            jpaOrderProductEntity.setId(JPAOrderProductId);
            jpaOrderProductEntity.setQtItem(p.getQtItem());
            jpaOrderProductEntity.setVlQtItem(p.getVlQtItem());
            jpaOrderProductEntity.setOrder(orderEntity);
            jpaOrderProductList.add(jpaOrderProductEntity);
        }
        orderEntity.setProdutos(jpaOrderProductList);
        return orderEntity;
    }

    @Override
    public Page<OrderResponseDTO> toOrderGetResponseDTO(Page<JPAOrderEntity> jpaOrderEntityPage) {
        return jpaOrderEntityPage.map(orderEntity -> {
            List<ProductResponse> productResponses = new ArrayList<>();
            if (orderEntity.getProdutos() != null) {
                for (var prod : orderEntity.getProdutos()) {
                    productResponses.add(new ProductResponse(
                            prod.getProductId(),
                            prod.getQtItem(),
                            prod.getVlQtItem()
                    ));
                }
            }
            return new OrderResponseDTO(
                    orderEntity.getId(),
                    orderEntity.getTotalAmountOrder(),
                    orderEntity.getMinute(),
                    orderEntity.getClientId(),
                    productResponses,
                    orderEntity.getTimestamp()
            );
        });
    }

    @Override
    public Optional<OrderResponseDTO> toOrderGetResponseDTO(Optional<JPAOrderEntity> jpaOrderEntity) {
        OrderResponseDTO orderResponseDTO = null;
        if (jpaOrderEntity.isPresent()) {
            JPAOrderEntity orderEntity = jpaOrderEntity.get();
            List<ProductResponse> productResponses = new ArrayList<>();
            if (orderEntity.getProdutos() != null) {
                for (var prod : orderEntity.getProdutos()) {
                    productResponses.add(new ProductResponse(
                            prod.getProductId(),
                            prod.getQtItem(),
                            prod.getVlQtItem()
                    ));
                }
            }
            orderResponseDTO = new OrderResponseDTO(
                    orderEntity.getId(),
                    orderEntity.getTotalAmountOrder(),
                    orderEntity.getMinute(),
                    orderEntity.getClientId(),
                    productResponses,
                    orderEntity.getTimestamp()
            );
            return Optional.of(orderResponseDTO);
        }
        return Optional.empty();
    }
}

