package com.store.soattechchallenge.preparation.infrastructure.adapters.out.mapper.impl;

import com.store.soattechchallenge.preparation.domain.model.Preparation;
import com.store.soattechchallenge.preparation.domain.model.PreparationStatus;
import com.store.soattechchallenge.preparation.infrastructure.adapters.in.dto.PreparationResponseDTO;
import com.store.soattechchallenge.preparation.infrastructure.adapters.out.model.JpaPreparation;
import com.store.soattechchallenge.preparation.infrastructure.adapters.out.mapper.PreparationMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PreparationMapperImpl implements PreparationMapper {
    @Override
    public Preparation toDomain(JpaPreparation jpaPreparation) {
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

    @Override
    public PreparationResponseDTO toDTO(Preparation preparation) {
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

    @Override
    public JpaPreparation toJPA(Preparation preparation) {
        return new JpaPreparation(preparation);
    }
}
