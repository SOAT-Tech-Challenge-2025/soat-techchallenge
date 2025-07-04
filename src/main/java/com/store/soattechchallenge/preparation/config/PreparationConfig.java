package com.store.soattechchallenge.preparation.config;

import com.store.soattechchallenge.preparation.application.gateways.PreparationGateway;
import com.store.soattechchallenge.preparation.application.usecases.*;
import com.store.soattechchallenge.preparation.controller.PreparationController;
import com.store.soattechchallenge.preparation.infrastructure.gateways.PreparationJpaGateway;
import com.store.soattechchallenge.preparation.infrastructure.jpa.PreparationJpaRepository;
import com.store.soattechchallenge.preparation.infrastructure.mappers.PreparationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PreparationConfig {
    @Bean
    PreparationMapper preparationMapper() {
        return new PreparationMapper();
    }

    @Bean
    PreparationGateway preparationGateway(
            PreparationJpaRepository preparationJpaRepository,
            PreparationMapper preparationMapper
    ) {
        return new PreparationJpaGateway(
                preparationJpaRepository,
                preparationMapper
        );
    }

    @Bean
    CreatePreparationUseCase createPreparationUseCase(PreparationGateway preparationGateway) {
        return new CreatePreparationUseCase(preparationGateway);
    }

    @Bean
    StartNextPreparationUseCase startNextPreparationUseCase(PreparationGateway preparationGateway) {
        return new StartNextPreparationUseCase(preparationGateway);
    }

    @Bean
    MarkPreparationAsReadyUseCase markPreparationAsReadyUseCase(PreparationGateway preparationGateway) {
        return new MarkPreparationAsReadyUseCase(preparationGateway);
    }

    @Bean
    MarkPreparationAsCompletedUseCase markPreparationAsCompletedUseCase(PreparationGateway preparationGateway) {
        return new MarkPreparationAsCompletedUseCase(preparationGateway);
    }

    @Bean
    GetWaitingListUseCase getWaitingListUseCase(PreparationGateway preparationGateway) {
        return new GetWaitingListUseCase(preparationGateway);
    }

    @Bean
    PreparationController preparationController(
            CreatePreparationUseCase createUseCase,
            StartNextPreparationUseCase startNextUseCase,
            MarkPreparationAsReadyUseCase markAsReadyUseCase,
            MarkPreparationAsCompletedUseCase markAsCompletedUseCase,
            GetWaitingListUseCase getWaitingListUseCase
    ) {
        return new PreparationController(
                createUseCase,
                startNextUseCase,
                markAsReadyUseCase,
                markAsCompletedUseCase,
                getWaitingListUseCase
        );
    }
}
