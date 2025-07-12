package com.store.soattechchallenge.shoppingCart.order.controller;

import com.store.soattechchallenge.shoppingCart.order.application.usecases.CreateOrderUseCase;
import com.store.soattechchallenge.shoppingCart.order.application.usecases.FindOrdersUseCase;
import com.store.soattechchallenge.shoppingCart.order.application.usecases.UpdateOrderUseCase;
import com.store.soattechchallenge.shoppingCart.order.application.usecases.command.OrderRequestCommand;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderRequestDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


public class OrderMainController {

    public final CreateOrderUseCase createOrderUseCase;
    public final FindOrdersUseCase findOrdersUseCase;
    public final UpdateOrderUseCase updateOrderUseCase;

    public OrderMainController(CreateOrderUseCase createOrderUseCase, FindOrdersUseCase findOrdersUseCase, UpdateOrderUseCase updateOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.findOrdersUseCase = findOrdersUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
    }

    public Optional<OrderResponseDTO> createProduct(OrderRequestCommand command) {
        return createOrderUseCase.saveOrder(command);
    }

    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        return findOrdersUseCase.getAllOrders(pageable);
    }

    public Optional<OrderResponseDTO> getOrdeById(String id) {
        return findOrdersUseCase.getOrdeById(id);
    }

    public Optional<OrderResponseDTO> updateOrder(String id, OrderRequestDTO orderRequestDTO){
        OrderResponseDTO orderResponseDTO = getOrdeById(id).orElseThrow(() -> new CustomException(
                "Pedido não encontrado",
                HttpStatus.NOT_FOUND,
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                LocalDateTime.now(),
                UUID.randomUUID()
        ));

       return updateOrderUseCase.updateOrder(id, orderRequestDTO);
    }
}
