package com.store.soattechchallenge.pagamento.domain.repository;

import com.store.soattechchallenge.pagamento.domain.exceptions.NotFound;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;

public interface PagamentoRepository {
    public Pagamento findById (String id);
    public Pagamento findByIdExterno (String idExterno);
    public Pagamento save (Pagamento pagamento);
}
