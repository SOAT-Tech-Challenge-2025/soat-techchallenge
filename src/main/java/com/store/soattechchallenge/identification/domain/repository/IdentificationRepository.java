package com.store.soattechchallenge.identification.domain.repository;

import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.model.JpaIdentification;

import java.util.Optional;

public interface IdentificationRepository {

    Identification createClient(Identification identification);

    Optional<JpaIdentification> findByDocumentOrEmail(String id_document, String id_email);

    boolean existsByNumberDocument(String numberDocument);

    boolean existsByEmail(String email);
}
