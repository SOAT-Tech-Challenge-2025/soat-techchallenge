package com.store.soattechchallenge.identification.domain.repository;

import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.model.JpaIdentification;

import java.util.Optional;
import java.util.UUID;

public interface IdentificationRepository {

    Identification createClient(Identification identification);

    Optional<JpaIdentification> getByClient(UUID identification_id);
}
