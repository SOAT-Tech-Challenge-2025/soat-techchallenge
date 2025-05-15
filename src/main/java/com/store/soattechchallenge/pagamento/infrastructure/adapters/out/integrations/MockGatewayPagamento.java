package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations;

import com.store.soattechchallenge.pagamento.domain.GatewayPagamento;
import com.store.soattechchallenge.pagamento.domain.model.GatewayPagamentoResponse;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.domain.model.Produto;

import java.util.List;

public class MockGatewayPagamento implements GatewayPagamento {
    @Override
    public GatewayPagamentoResponse create(Pagamento pagamento, List<Produto> produtos) {
        return new GatewayPagamentoResponse(pagamento.getId(), "saiodnasodna");
    }
}
