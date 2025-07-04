package com.store.soattechchallenge.preparation.infrastructure.mappers;

import com.store.soattechchallenge.preparation.domain.PreparationStatus;
import com.store.soattechchallenge.preparation.domain.entites.Preparation;
import com.store.soattechchallenge.preparation.infrastructure.api.dto.PreparationResponseDTO;
import com.store.soattechchallenge.preparation.infrastructure.jpa.JpaPreparation;

import java.util.Optional;

public class PreparationMapper {
    public Preparation fromJpaToDomain(JpaPreparation jpaPreparation) {
        return new Preparation(
                jpaPreparation.getId(),
                jpaPreparation.getPreparationPosition(),
                jpaPreparation.getPreparationTime(),
                jpaPreparation.getEstimatedReadyTime(),
                PreparationStatus.valueOf(jpaPreparation.getPreparationStatus()),
                jpaPreparation.getCreatedAt(),
                jpaPreparation.getTimestamp()
        );
    }

    public PreparationResponseDTO fromDomainToDTO(Preparation preparation) {
        return new PreparationResponseDTO(
                preparation.getId(),
                Optional.ofNullable(preparation.getPreparationPosition()),
                preparation.getPreparationTime(),
                Optional.ofNullable(preparation.getEstimatedReadyTime()),
                preparation.getPreparationStatus(),
                preparation.getCreatedAt(),
                preparation.getTimestamp()
        );
    }

    public JpaPreparation fromDomainToJpa(Preparation preparation) {
        return new JpaPreparation(preparation);
    }
}
