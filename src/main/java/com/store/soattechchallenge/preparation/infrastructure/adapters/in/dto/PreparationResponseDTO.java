package com.store.soattechchallenge.preparation.infrastructure.adapters.in.dto;

import com.store.soattechchallenge.preparation.domain.model.PreparationStatus;

import java.time.LocalDateTime;
import java.util.Optional;

public record PreparationResponseDTO(
        String id,
        Optional<Integer> preparationPosition,
        Integer preparationTime,
        Optional<LocalDateTime> estimatedReadyTime,
        PreparationStatus preparationStatus,
        LocalDateTime createdAt,
        LocalDateTime timestamp
) {
}
