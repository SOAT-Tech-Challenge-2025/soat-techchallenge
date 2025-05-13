package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations;

import com.store.soattechchallenge.pagamento.domain.GatewayPagamento;
import com.store.soattechchallenge.pagamento.domain.model.GatewayPagamentoResponse;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.domain.model.Produto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockGatewayPagamento implements GatewayPagamento {
    @Override
    public GatewayPagamentoResponse create(Pagamento pagamento, List<Produto> produtos) {
        return new GatewayPagamentoResponse(pagamento.id(), "saiodnasodna");
    }
}
