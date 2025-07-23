package com.store.soattechchallenge.identification.usecases;

import com.store.soattechchallenge.identification.gateways.IdentificationRepositoryGateway;
import com.store.soattechchallenge.identification.usecases.commands.CreateClientCommand;
import com.store.soattechchallenge.identification.domain.entities.Identification;

import java.time.LocalDateTime;

public class CreateClientUseCase {

    private final IdentificationRepositoryGateway identificationRepositoryGateway;

    public CreateClientUseCase(IdentificationRepositoryGateway identificationRepositoryGateway) {
        this.identificationRepositoryGateway = identificationRepositoryGateway;
    }

    public Identification createClient(CreateClientCommand createClientCommand) {
        identificationRepositoryGateway.existsByNumberDocument(createClientCommand.numberDocument());

        identificationRepositoryGateway.existsByEmail(createClientCommand.email());

        Identification mapperIdentification = new Identification();
        mapperIdentification.setNameClient(createClientCommand.nameClient());
        mapperIdentification.setNumberDocument(createClientCommand.numberDocument());
        mapperIdentification.setEmail(createClientCommand.email());
        mapperIdentification.setCreatedAt(LocalDateTime.now());
        mapperIdentification.setUpdatedAt(LocalDateTime.now());
        identificationRepositoryGateway.createClient(mapperIdentification);

        return mapperIdentification;
    }
}
