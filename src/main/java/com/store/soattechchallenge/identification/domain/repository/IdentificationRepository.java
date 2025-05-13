package com.store.soattechchallenge.identification.domain.repository;

import com.store.soattechchallenge.identification.domain.model.Identification;

public interface IdentificationRepository {

    Identification createClient(Identification identification);
}
