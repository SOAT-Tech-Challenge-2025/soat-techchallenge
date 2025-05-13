package com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto;

import com.store.soattechchallenge.pagamento.domain.model.StatusPagamento;

public record PagamentoFinishRequestDTO (
        StatusPagamento stPagamento
) {
}
