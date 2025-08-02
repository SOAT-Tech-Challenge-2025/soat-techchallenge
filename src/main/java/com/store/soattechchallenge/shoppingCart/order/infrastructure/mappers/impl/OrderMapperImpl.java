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
    public Page<OrderResponseDTO> toOrderGetResponseDTO(Page<Order> orders) {
        return orders.map(order -> {
            List<ProductResponse> productResponses = new ArrayList<>();
            if (order.getOrderProducts() != null) {
                for (OrderProduct prod : order.getOrderProducts()) {
                    productResponses.add(new ProductResponse(
                            prod.getProductId(),
                            prod.getQtItem(),
                            prod.getVlQtItem()
                    ));
                }
            }
            return new OrderResponseDTO(
                    order.getId(),
                    order.getTotalAmountOrder(),
                    order.getMinute(),
                    order.getClientId(),
                    productResponses,
                    order.getTimestamp()
            );
        });
    }

    @Override
    public Optional<OrderResponseDTO> toOrderGetResponseDTO(Optional<Order> order) {
        return order.map(o -> {
            List<ProductResponse> productResponses = new ArrayList<>();
            if (o.getOrderProducts() != null) {
                for (OrderProduct prod : o.getOrderProducts()) {
                    productResponses.add(new ProductResponse(
                            prod.getProductId(),
                            prod.getQtItem(),
                            prod.getVlQtItem()
                    ));
                }
            }
            return new OrderResponseDTO(
                    o.getId(),
                    o.getTotalAmountOrder(),
                    o.getMinute(),
                    o.getClientId(),
                    productResponses,
                    o.getTimestamp()
            );
        });
    }

    @Override
    public Optional<Order> entityToOrder(Optional<JPAOrderEntity> jpaOrderEntity) {
        if (jpaOrderEntity.isEmpty()) {
            return Optional.empty();
        }
        JPAOrderEntity entity = jpaOrderEntity.get();
        List<OrderProduct> orderProducts = new ArrayList<>();
        if (entity.getProdutos() != null) {
            for (JPAOrderProductEntity prod : entity.getProdutos()) {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setProductId(prod.getProductId());
                orderProduct.setQtItem(prod.getQtItem());
                orderProduct.setVlQtItem(prod.getVlQtItem());
                orderProducts.add(orderProduct);
            }
        }
        Order order = new Order();
        order.setId(entity.getId());
        order.setTotalAmountOrder(entity.getTotalAmountOrder());
        order.setMinute(entity.getMinute());
        order.setClientId(entity.getClientId());
        order.setOrderProducts(orderProducts);
        order.setTimestamp(entity.getTimestamp());
        return Optional.of(order);
    }

    @Override
    public Page<Order> entitiesToOrders(Page<JPAOrderEntity> jpaOrderEntities) {
        return jpaOrderEntities.map(entity -> {
            List<OrderProduct> orderProducts = new ArrayList<>();
            if (entity.getProdutos() != null) {
                for (JPAOrderProductEntity prod : entity.getProdutos()) {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setProductId(prod.getProductId());
                    orderProduct.setQtItem(prod.getQtItem());
                    orderProduct.setVlQtItem(prod.getVlQtItem());
                    orderProducts.add(orderProduct);
                }
            }
            Order order = new Order();
            order.setId(entity.getId());
            order.setTotalAmountOrder(entity.getTotalAmountOrder());
            order.setMinute(entity.getMinute());
            order.setClientId(entity.getClientId());
            order.setOrderProducts(orderProducts);
            order.setTimestamp(entity.getTimestamp());
            return order;
        });
    }
}

