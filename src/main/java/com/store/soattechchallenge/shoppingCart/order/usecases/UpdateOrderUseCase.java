package com.store.soattechchallenge.shoppingCart.order.usecases;

import com.store.soattechchallenge.shoppingCart.order.domain.entities.Order;
import com.store.soattechchallenge.shoppingCart.order.domain.entities.OrderProduct;
import com.store.soattechchallenge.shoppingCart.order.gateways.OrderRepositoryGateways;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderRequestDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.utils.OrderUtils;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.gateways.OrderRepositoryGatewaysImpl;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.JPAOrderEntity;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UpdateOrderUseCase {
    public final OrderRepositoryGateways adaptersRepository;

    public UpdateOrderUseCase(OrderRepositoryGateways adaptersRepository) {
        this.adaptersRepository = adaptersRepository;
    }

    public  Optional<JPAOrderEntity>  updateOrder(String id, OrderRequestDTO orderRequestDTO){
        List<OrderProduct> orderProducts = OrderUtils.groupAndSumProducts(orderRequestDTO.getProducts(), id);
        double totalOrder = orderProducts.stream()
                .mapToDouble(OrderProduct::getVlQtItem)
                .sum();
        Order orderRequestModelModel = new Order(id, totalOrder, OrderUtils.somarPreparationTime(orderRequestDTO.getProducts()), orderRequestDTO.getClientId(), orderProducts);

        try {
            adaptersRepository.save(orderRequestModelModel);
        }catch (Exception e) {
            throw new CustomException(
                    "Erro ao atualizar o pedido",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return adaptersRepository.findOrderById(id);
    }
}
