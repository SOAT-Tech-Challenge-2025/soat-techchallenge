package com.store.soattechchallenge.pagamento.domain.model;

import java.time.LocalDateTime;

public record Pagamento(
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
