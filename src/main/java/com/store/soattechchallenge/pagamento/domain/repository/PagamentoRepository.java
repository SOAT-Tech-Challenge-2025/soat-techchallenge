package com.store.soattechchallenge.pagamento.domain.repository;

import com.store.soattechchallenge.pagamento.domain.exceptions.NotFound;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;

public interface PagamentoRepository {
    public Pagamento findById (String id);
    public Boolean existsById(String id);
    public Boolean existsByIdExterno(String id);
    public Pagamento save (Pagamento pagamento);
}
