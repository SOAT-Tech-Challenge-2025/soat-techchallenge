package com.store.soattechchallenge.preparation.application.usecases;

import com.store.soattechchallenge.preparation.domain.events.PaymentClosedEvent;
import com.store.soattechchallenge.preparation.application.usecases.commands.CreatePreparationCommand;
import com.store.soattechchallenge.shoppingCart.order.application.usecases.FindOrdersUseCase;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;

import java.util.Optional;

public class HandlePaymentClosedEventUseCase {
    private final CreatePreparationUseCase createPreparationUseCase;
    private final FindOrdersUseCase findOrdersUseCase;

    public HandlePaymentClosedEventUseCase(
            CreatePreparationUseCase createPreparationUseCase,
            FindOrdersUseCase findOrdersUseCase
    ) {
        this.createPreparationUseCase = createPreparationUseCase;
        this.findOrdersUseCase = findOrdersUseCase;
    }

    public void handle(PaymentClosedEvent event) {
        Optional<OrderResponseDTO> order = this.findOrdersUseCase.getOrdeById(event.getPaymentId());
        if (order.isEmpty()) {
            throw new IllegalArgumentException("Order with ID " + event.getPaymentId() + " not found");
        }

        CreatePreparationCommand createPreparationCommand = new CreatePreparationCommand(
                event.getPaymentId(),
                order.get().getPreparationTime()
        );

        this.createPreparationUseCase.execute(createPreparationCommand);
    }
}
