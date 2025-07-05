package com.store.soattechchallenge.preparation.config;

import com.store.soattechchallenge.preparation.application.gateways.PreparationRepositoryGateway;
import com.store.soattechchallenge.preparation.application.usecases.*;
import com.store.soattechchallenge.preparation.controller.PreparationController;
import com.store.soattechchallenge.preparation.infrastructure.gateways.PreparationRepositoryJpaGateway;
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
