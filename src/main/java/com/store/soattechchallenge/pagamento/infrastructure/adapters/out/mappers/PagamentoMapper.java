package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.PagamentoResponseDTO;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.model.JPAPagamento;

public interface PagamentoMapper {
    public Pagamento toDomain (JPAPagamento jpaPagamento);
    public PagamentoResponseDTO toDTO (Pagamento pagamento);
    public JPAPagamento toJPA(Pagamento pagamento);
}
