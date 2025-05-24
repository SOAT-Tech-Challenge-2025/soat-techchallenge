package com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto;

import com.store.soattechchallenge.pagamento.domain.model.StatusPagamento;

import java.time.LocalDateTime;

public record PagamentoResponseDTO(
        String id,
        String idExterno,
        StatusPagamento stPagamento,
        Double vlTotalPedido,
        String codigoQr,
        LocalDateTime expiracao,
        LocalDateTime dtInclusao,
        LocalDateTime timestamp
) {

}
