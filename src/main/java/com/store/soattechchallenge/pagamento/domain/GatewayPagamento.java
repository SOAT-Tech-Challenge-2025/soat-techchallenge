package com.store.soattechchallenge.pagamento.domain;

import com.store.soattechchallenge.pagamento.domain.model.GatewayPagamentoResponse;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.domain.model.Produto;

import java.util.List;

public interface GatewayPagamento {
    public GatewayPagamentoResponse create(Pagamento pagamento, List<Produto> produtos);
}
