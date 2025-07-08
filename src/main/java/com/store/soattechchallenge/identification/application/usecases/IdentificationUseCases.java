package com.store.soattechchallenge.identification.application.usecases;

import com.store.soattechchallenge.identification.domain.entities.Identification;
import com.store.soattechchallenge.identification.infrastructure.api.dto.IdentificationRequestDTO;

import java.util.Optional;

public interface IdentificationUseCases {

    public Identification createClient(IdentificationRequestDTO identificationRequestDTO);

    public Optional<Identification> findByDocumentOrEmail(String documentOrEmail);

    public String authenticate(String documentNumber, String email);
}
