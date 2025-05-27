package com.store.soattechchallenge.identification.application.usecases;

import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.dto.IdentificationRequestDTO;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.model.JpaIdentification;

import java.util.Optional;

public interface IdentificationUseCases {

    public Identification createClient(IdentificationRequestDTO identificationRequestDTO);

    public Optional<Identification> findByDocumentOrEmail(String documentOrEmail);

    public String authenticate(String documentNumber, String email);
}
