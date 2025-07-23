package com.store.soattechchallenge.preparation.presenters;

import com.store.soattechchallenge.preparation.domain.entites.Preparation;
import com.store.soattechchallenge.preparation.infrastructure.api.dto.ItemsResponseDTO;
import com.store.soattechchallenge.preparation.infrastructure.api.dto.PreparationResponseDTO;
import com.store.soattechchallenge.preparation.infrastructure.mappers.PreparationMapper;

import java.util.List;
import java.util.stream.Collectors;

public class WaitingListHttpPresenter {
    private final PreparationMapper preparationMapper;

    public WaitingListHttpPresenter(PreparationMapper preparationMapper) {
        this.preparationMapper = preparationMapper;
    }

    public ItemsResponseDTO<PreparationResponseDTO> present(List<Preparation> preparations) {
        List<PreparationResponseDTO> items = preparations
                .stream()
                .map(this.preparationMapper::fromDomainToDTO)
                .collect(Collectors.toList());

        return new ItemsResponseDTO<>(items);
    }
}
