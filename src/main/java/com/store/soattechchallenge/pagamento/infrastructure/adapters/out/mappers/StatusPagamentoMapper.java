package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.pagamento.domain.model.StatusPagamento;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPOrderStatus;

public interface StatusPagamentoMapper {
    public StatusPagamento toDomain(MPOrderStatus mpStatus);
}
