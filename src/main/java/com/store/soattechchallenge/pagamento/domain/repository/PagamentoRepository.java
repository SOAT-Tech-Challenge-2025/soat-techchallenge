package com.store.soattechchallenge.pagamento.domain.repository;

import com.store.soattechchallenge.pagamento.domain.model.Pagamento;

public interface PagamentoRepository {
    public Pagamento findById (String id);
    public Pagamento save (Pagamento pagamento);
}
