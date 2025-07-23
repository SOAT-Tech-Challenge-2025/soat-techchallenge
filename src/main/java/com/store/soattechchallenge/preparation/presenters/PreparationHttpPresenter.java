package com.store.soattechchallenge.preparation.presenters;

import com.store.soattechchallenge.preparation.domain.entites.Preparation;
import com.store.soattechchallenge.preparation.infrastructure.api.dto.PreparationResponseDTO;
import com.store.soattechchallenge.preparation.infrastructure.mappers.PreparationMapper;

public class PreparationHttpPresenter {
    private final PreparationMapper preparationMapper;

    public PreparationHttpPresenter(PreparationMapper preparationMapper) {
        this.preparationMapper = preparationMapper;
    }

    public PreparationResponseDTO present(Preparation preparation) {
        return this.preparationMapper.fromDomainToDTO(preparation);
    }
}
