package com.store.soattechchallenge.identification.application.gateways;

import com.store.soattechchallenge.identification.domain.entities.Identification;

import java.util.Optional;

public interface IdentificationRepositoryGateway {

    Identification createClient(Identification identification);

    Optional<Identification> findByDocumentOrEmail(String id_document, String id_email);

    boolean existsByNumberDocument(String numberDocument);

    boolean existsByEmail(String email);
}
