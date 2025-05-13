package com.store.soattechchallenge.identification.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.domain.repository.IdentificationRepository;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.model.JpaIdentification;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.repository.JpaIdentificationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class IdentificationRepositoryImpl implements IdentificationRepository {

    private final JpaIdentificationRepository jpaIdentificationRepository;

    public IdentificationRepositoryImpl(JpaIdentificationRepository jpaIdentificationRepository) {
        this.jpaIdentificationRepository = jpaIdentificationRepository;
    }

    @Override
    public Identification createClient(Identification identification) {
        JpaIdentification jpaIdentification = new JpaIdentification(identification);
        this.jpaIdentificationRepository.save(jpaIdentification);
        return new Identification(jpaIdentification.getId(), jpaIdentification.getNameClient(), jpaIdentification.getEmail(),
                jpaIdentification.getNumberDocument(), jpaIdentification.getCreatedAt(), jpaIdentification.getUpdatedAt());
    }
}
