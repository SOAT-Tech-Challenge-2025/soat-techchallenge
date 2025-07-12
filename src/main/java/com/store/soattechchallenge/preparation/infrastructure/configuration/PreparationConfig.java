package com.store.soattechchallenge.preparation.infrastructure.configuration;

import com.store.soattechchallenge.preparation.domain.events.PaymentClosedEvent;
import com.store.soattechchallenge.preparation.application.gateways.PreparationRepositoryGateway;
import com.store.soattechchallenge.preparation.application.usecases.*;
import com.store.soattechchallenge.preparation.controller.PreparationController;
import com.store.soattechchallenge.preparation.infrastructure.gateways.PreparationRepositoryJpaGateway;
import com.store.soattechchallenge.preparation.infrastructure.jpa.PreparationJpaRepository;
import com.store.soattechchallenge.preparation.infrastructure.mappers.PreparationMapper;
import com.store.soattechchallenge.shoppingCart.order.application.usecases.FindOrdersUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PreparationConfig {
    @Bean
    PreparationMapper preparationMapper() {
        return new PreparationMapper();
    }

    @Bean
    PreparationRepositoryGateway preparationRepositoryGateway(
            PreparationJpaRepository preparationJpaRepository,
            PreparationMapper preparationMapper
    ) {
        return new PreparationRepositoryJpaGateway(
                preparationJpaRepository,
                preparationMapper
        );
    }

    @Bean
    CreatePreparationUseCase createPreparationUseCase(PreparationRepositoryGateway preparationRepositoryGateway) {
        return new CreatePreparationUseCase(preparationRepositoryGateway);
    }

    @Bean
    StartNextPreparationUseCase startNextPreparationUseCase(PreparationRepositoryGateway preparationRepositoryGateway) {
        return new StartNextPreparationUseCase(preparationRepositoryGateway);
    }

    @Bean
    MarkPreparationAsReadyUseCase markPreparationAsReadyUseCase(PreparationRepositoryGateway preparationRepositoryGateway) {
        return new MarkPreparationAsReadyUseCase(preparationRepositoryGateway);
    }

    @Bean
    MarkPreparationAsCompletedUseCase markPreparationAsCompletedUseCase(PreparationRepositoryGateway preparationRepositoryGateway) {
        return new MarkPreparationAsCompletedUseCase(preparationRepositoryGateway);
    }

    @Bean
    GetWaitingListUseCase getWaitingListUseCase(PreparationRepositoryGateway preparationRepositoryGateway) {
        return new GetWaitingListUseCase(preparationRepositoryGateway);
    }

    @Bean
    HandlePaymentClosedEventUseCase handlePaymentClosedEventUseCase(
            CreatePreparationUseCase createPreparationUseCase,
            FindOrdersUseCase findOrdersUseCase
    ) {
        return new HandlePaymentClosedEventUseCase(createPreparationUseCase, findOrdersUseCase);
    }

    @Bean
    PreparationController preparationController(
            CreatePreparationUseCase createUseCase,
            StartNextPreparationUseCase startNextUseCase,
            MarkPreparationAsReadyUseCase markAsReadyUseCase,
            MarkPreparationAsCompletedUseCase markAsCompletedUseCase,
            GetWaitingListUseCase getWaitingListUseCase,
            HandlePaymentClosedEventUseCase handlePaymentClosedEventUseCase
    ) {
        return new PreparationController(
                createUseCase,
                startNextUseCase,
                markAsReadyUseCase,
                markAsCompletedUseCase,
                getWaitingListUseCase,
                handlePaymentClosedEventUseCase
        );
    }

    @Bean
    public Consumer<PaymentClosedEvent> consumePaymentClosedEvent(PreparationController preparationController) {
        return preparationController::handlePaymentClosedEvent;
    }
}
