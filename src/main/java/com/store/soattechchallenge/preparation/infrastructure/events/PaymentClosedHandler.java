package com.store.soattechchallenge.preparation.infrastructure.events;

import com.store.soattechchallenge.preparation.controller.PreparationController;
import com.store.soattechchallenge.preparation.infrastructure.jpa.PreparationJpaRepository;
import com.store.soattechchallenge.preparation.infrastructure.mappers.PreparationMapper;
import com.store.soattechchallenge.preparation.usecases.commands.CreatePreparationCommand;
import com.store.soattechchallenge.shoppingCart.order.controller.OrderMainController;
import com.store.soattechchallenge.shoppingCart.order.gateways.OrderRepositoryGateways;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.gateways.OrderRepositoryGatewaysImpl;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.OrderAdaptersGetRepository;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.OrderMapper;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.Optional;

public class PaymentClosedHandler {
    private final PreparationController preparationController;
    private final OrderMainController orderController;

    public PaymentClosedHandler(
            PreparationJpaRepository preparationJpaRepository,
            PreparationMapper preparationMapper,
            OrderAdaptersGetRepository ordersJpaRepository,
            OrderMapper orderMapper,
            StreamBridge streamBridge
    ) {
        OrderRepositoryGateways orderRepositoryGateways = new OrderRepositoryGatewaysImpl(
                ordersJpaRepository, orderMapper
        );

        this.preparationController = new PreparationController(preparationJpaRepository, preparationMapper);
        this.orderController = new OrderMainController(orderRepositoryGateways, orderMapper, streamBridge);
    }

    public void handle(PaymentClosedEvent event) {
        Optional<OrderResponseDTO> order = this.orderController.getOrdeById(event.getPaymentId());
        if (order.isEmpty()) {
            throw new RuntimeException("Order " + event.getPaymentId() + " not found");
        }

        CreatePreparationCommand command = new CreatePreparationCommand(
                event.getPaymentId(),
                order.get().getPreparationTime()
        );

        this.preparationController.create(command);
    }
}
