package com.store.soattechchallenge.identification.application.usecases;

import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.domain.model.IdentificationDTO;

import java.util.UUID;

public interface IdentificationUseCases {

    public Identification createClient(IdentificationDTO identificationDTO);

    public IdentificationDTO getByClient(UUID identification_id);
}
