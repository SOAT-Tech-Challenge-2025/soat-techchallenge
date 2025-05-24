package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers.impl;

import com.store.soattechchallenge.pagamento.domain.model.StatusPagamento;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPOrderStatus;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers.StatusPagamentoMapper;
import org.springframework.stereotype.Component;

@Component
public class StatusPagamentoMapperImpl implements StatusPagamentoMapper {
    public StatusPagamento toDomain(MPOrderStatus mpStatus) {
        return switch (mpStatus) {
            case opened -> StatusPagamento.OPENED;
            case closed -> StatusPagamento.CLOSED;
            case expired -> StatusPagamento.EXPIRED;
        };
    }
}
