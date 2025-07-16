package com.store.soattechchallenge.payment.application.usecases;

import com.store.soattechchallenge.payment.application.gateways.PaymentProductDTO;
import com.store.soattechchallenge.payment.application.usecases.commands.CreatePaymentCommand;
import com.store.soattechchallenge.payment.domain.events.OrderCreatedEvent;
import com.store.soattechchallenge.shoppingCart.order.application.usecases.FindOrdersUseCase;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;

import java.util.Optional;
import java.util.stream.Collectors;

public class HandleOrderCreatedUseCase {
    private final CreatePaymentUseCase createPaymentUseCase;
    private final FindOrdersUseCase findOrdersUseCase;

    public HandleOrderCreatedUseCase(CreatePaymentUseCase createPaymentUseCase, FindOrdersUseCase findOrdersUseCase) {
        this.createPaymentUseCase = createPaymentUseCase;
        this.findOrdersUseCase = findOrdersUseCase;
    }

    public void handle(OrderCreatedEvent event) {
        Optional<OrderResponseDTO> orderResponseDTOOptional = this.findOrdersUseCase.getOrdeById(event.getOrderId());
        if (orderResponseDTOOptional.isEmpty()) {
            return;
        }

        OrderResponseDTO orderResponseDTO = orderResponseDTOOptional.get();
        CreatePaymentCommand command = new CreatePaymentCommand(
                orderResponseDTO.getOrderId(),
                orderResponseDTO.getTotalOrder(),
                orderResponseDTO
                        .getProducts()
                        .stream()
                        .map(productResponse -> new PaymentProductDTO(
                            productResponse.getProductId().toString(),
                            productResponse.getProductId().toString(),
                            productResponse.getVlUnitProduct(),
                            1
                        )).collect(Collectors.toList()));

        this.createPaymentUseCase.execute(command);
    }
}
