package com.store.soattechchallenge.identification.controller;

import com.store.soattechchallenge.identification.domain.entities.Identification;
import com.store.soattechchallenge.identification.gateways.IdentificationRepositoryGateway;
import com.store.soattechchallenge.identification.infrastructure.security.JwtTokenSecurity;
import com.store.soattechchallenge.identification.usecases.CreateClientUseCase;
import com.store.soattechchallenge.identification.usecases.GetClientUseCase;
import com.store.soattechchallenge.identification.usecases.commands.CreateClientCommand;

import java.util.Optional;

public class IdentificationController {
    private final IdentificationRepositoryGateway identificationRepositoryGateway;
    private final JwtTokenSecurity jwtTokenSecurity;

    public IdentificationController(
            IdentificationRepositoryGateway identificationRepositoryGateway, JwtTokenSecurity jwtTokenSecurity
    ) {
        this.identificationRepositoryGateway = identificationRepositoryGateway;
        this.jwtTokenSecurity = jwtTokenSecurity;
    }

    public Identification create(CreateClientCommand command) {
        CreateClientUseCase createClientUseCase = new CreateClientUseCase(this.identificationRepositoryGateway);
        return createClientUseCase.createClient(command);
    }

    public Optional<Identification> getClient(String documentOrEmail) {
        GetClientUseCase getClientUseCase = new GetClientUseCase(this.identificationRepositoryGateway, this.jwtTokenSecurity);
        return getClientUseCase.findByDocumentOrEmail(documentOrEmail);
    }
}
