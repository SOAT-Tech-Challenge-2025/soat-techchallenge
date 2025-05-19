package com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.in.rest.dto;

import com.store.soattechchallenge.preparacao.domain.model.StatusPreparacao;

import java.time.LocalDateTime;
import java.util.Optional;

public record PreparacaoResponseDTO (
        String id,
        Optional<Integer> posicaoPreparacao,
        Integer tempoDePreparacao,
        Optional<LocalDateTime> estimativaDePronto,
        StatusPreparacao stPreparacao,
        LocalDateTime dtInclusao,
        LocalDateTime timestamp
) {
}
