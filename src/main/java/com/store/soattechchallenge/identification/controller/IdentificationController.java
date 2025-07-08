package com.store.soattechchallenge.identification.controller;

import com.store.soattechchallenge.identification.application.usecases.CreateClientUseCase;
import com.store.soattechchallenge.identification.application.usecases.GetClientUseCase;
import com.store.soattechchallenge.identification.application.usecases.commands.CreateClientCommand;
import com.store.soattechchallenge.identification.domain.entities.Identification;

import java.util.Optional;

public class IdentificationController {
    private final CreateClientUseCase createClientUseCase;
    private final GetClientUseCase getClientUseCase;

    public IdentificationController(
            CreateClientUseCase createClientUseCase,
            GetClientUseCase getClientUseCase
    ) {
        this.createClientUseCase = createClientUseCase;
        this.getClientUseCase = getClientUseCase;
    }

    public Identification create(CreateClientCommand command) {
        return this.createClientUseCase.createClient(command);
    }

    public Optional<Identification> getClient(String documentOrEmail) {
        return this.getClientUseCase.findByDocumentOrEmail(documentOrEmail);
    }
}
