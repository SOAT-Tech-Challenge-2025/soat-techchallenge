package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.pagamento.domain.model.StatusPagamento;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPOrderStatus;

public class StatusPagamentoMapper {
    public static StatusPagamento toStatusPagamento(MPOrderStatus mpStatus) {
        return switch (mpStatus) {
            case opened -> StatusPagamento.OPENED;
            case closed -> StatusPagamento.CLOSED;
            case expired -> StatusPagamento.EXPIRED;
        };
    }

    public static MPOrderStatus toMPOrderStatus(StatusPagamento statusPagamento) {
        return switch (statusPagamento) {
            case OPENED -> MPOrderStatus.opened;
            case CLOSED -> MPOrderStatus.closed;
            case EXPIRED -> MPOrderStatus.expired;
        };
    }
}
