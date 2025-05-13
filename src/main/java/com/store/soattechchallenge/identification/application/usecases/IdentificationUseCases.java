package com.store.soattechchallenge.identification.application.usecases;

import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.domain.model.IdentificationDTO;

public interface IdentificationUseCases {

    public Identification createClient(IdentificationDTO identificationDTO);
}
