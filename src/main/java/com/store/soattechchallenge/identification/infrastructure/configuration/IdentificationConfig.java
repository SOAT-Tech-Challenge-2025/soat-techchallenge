package com.store.soattechchallenge.identification.infrastructure.configuration;

import com.store.soattechchallenge.identification.gateways.IdentificationRepositoryGateway;
import com.store.soattechchallenge.identification.infrastructure.security.JwtRequestFilterSecurity;
import com.store.soattechchallenge.identification.usecases.CreateClientUseCase;
import com.store.soattechchallenge.identification.usecases.GetClientUseCase;
import com.store.soattechchallenge.identification.controller.IdentificationController;
import com.store.soattechchallenge.identification.infrastructure.gateways.IdentificationRepositoryJpaGateway;
import com.store.soattechchallenge.identification.infrastructure.jpa.JpaIdentificationRepository;
import com.store.soattechchallenge.identification.infrastructure.mappers.IdentificationMapper;
import com.store.soattechchallenge.identification.infrastructure.security.JwtTokenSecurity;
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
            IdentificationRepositoryGateway identificationRepositoryGateway, JwtTokenSecurity jwtTokenSecurity
    ) {
        return new IdentificationController(
                identificationRepositoryGateway,
                jwtTokenSecurity
        );
    }

    @Bean
    public JwtTokenSecurity jwtTokenSecurity() {
        return new JwtTokenSecurity();
    }

    @Bean
    public JwtRequestFilterSecurity jwtRequestFilterSecurity(JwtTokenSecurity jwtTokenSecurity, IdentificationRepositoryGateway identificationRepositoryGateway) {
        return new JwtRequestFilterSecurity(
                jwtTokenSecurity,
                identificationRepositoryGateway);
    }
}
