package com.store.soattechchallenge.preparation.infrastructure.adapters.out.mapper;

import com.store.soattechchallenge.preparation.domain.model.Preparation;
import com.store.soattechchallenge.preparation.infrastructure.adapters.in.dto.PreparationResponseDTO;
import com.store.soattechchallenge.preparation.infrastructure.adapters.out.model.JpaPreparation;

public interface PreparationMapper {
    public Preparation toDomain(JpaPreparation jpaPreparation);
    public PreparationResponseDTO toDTO (Preparation preparation);
    public JpaPreparation toJPA(Preparation preparation);
}
