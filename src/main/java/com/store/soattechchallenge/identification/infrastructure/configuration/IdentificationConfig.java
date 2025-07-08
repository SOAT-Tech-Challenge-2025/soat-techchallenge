package com.store.soattechchallenge.identification.infrastructure.configuration;

import com.store.soattechchallenge.identification.application.gateways.IdentificationRepositoryGateway;
import com.store.soattechchallenge.identification.application.usecases.CreateClientUseCase;
import com.store.soattechchallenge.identification.application.usecases.GetClientUseCase;
import com.store.soattechchallenge.identification.controller.IdentificationController;
import com.store.soattechchallenge.identification.infrastructure.gateways.IdentificationRepositoryJpaGateway;
import com.store.soattechchallenge.identification.infrastructure.jpa.JpaIdentificationRepository;
import com.store.soattechchallenge.identification.infrastructure.mappers.IdentificationMapper;
import com.store.soattechchallenge.identification.infrastructure.security.JwtTokenSecurity;
import com.store.soattechchallenge.preparation.application.gateways.PreparationRepositoryGateway;
import com.store.soattechchallenge.preparation.infrastructure.gateways.PreparationRepositoryJpaGateway;
import com.store.soattechchallenge.preparation.infrastructure.jpa.PreparationJpaRepository;
import com.store.soattechchallenge.preparation.infrastructure.mappers.PreparationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdentificationConfig {
    @Bean
    IdentificationMapper identificationMapper() {
        return new IdentificationMapper();
    }

    @Bean
    IdentificationRepositoryGateway identificationRepositoryGateway(
            JpaIdentificationRepository jpaIdentificationRepository
    ) {
        return new IdentificationRepositoryJpaGateway(
                jpaIdentificationRepository
        );
    }

    @Bean
    CreateClientUseCase createClientUseCase(IdentificationRepositoryGateway identificationRepositoryGateway) {
        return new CreateClientUseCase(identificationRepositoryGateway);
    }

    @Bean
    GetClientUseCase getClientUseCase(IdentificationRepositoryGateway identificationRepositoryGateway, JwtTokenSecurity jwtTokenSecurity) {
        return new GetClientUseCase(identificationRepositoryGateway, jwtTokenSecurity);
    }

    @Bean
    IdentificationController identificationController(
            CreateClientUseCase createClientUseCase,
            GetClientUseCase getClientUseCase
    ) {
        return new IdentificationController(
                createClientUseCase,
                getClientUseCase
        );
    }
}
