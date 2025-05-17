package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers.impl;

import com.store.soattechchallenge.pagamento.domain.model.StatusPagamento;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPOrderStatus;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers.StatusPagamentoMapper;
import org.springframework.stereotype.Component;

@Component
public class StatusPagamentoMapperImpl implements StatusPagamentoMapper {
    public StatusPagamento toStatusPagamento(MPOrderStatus mpStatus) {
        return switch (mpStatus) {
            case opened -> StatusPagamento.OPENED;
            case closed -> StatusPagamento.CLOSED;
            case expired -> StatusPagamento.EXPIRED;
        };
    }

    public MPOrderStatus toMPOrderStatus(StatusPagamento statusPagamento) {
        return switch (statusPagamento) {
            case OPENED -> MPOrderStatus.opened;
            case CLOSED -> MPOrderStatus.closed;
            case EXPIRED -> MPOrderStatus.expired;
        };
    }
}
