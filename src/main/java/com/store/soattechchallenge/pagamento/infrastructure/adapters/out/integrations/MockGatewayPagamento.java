package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations;

import com.store.soattechchallenge.pagamento.domain.GatewayPagamento;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.domain.model.Produto;
import com.store.soattechchallenge.pagamento.domain.model.StatusPagamento;

import java.time.LocalDateTime;
import java.util.List;

public class MockGatewayPagamento implements GatewayPagamento {
    @Override
    public Pagamento create(Pagamento pagamento, List<Produto> produtos) {
        LocalDateTime now = LocalDateTime.now();
        return new Pagamento(
                "1111",
                "11111",
                StatusPagamento.OPENED,
                20.00,
                "doipnqwd",
                LocalDateTime.now().plusMinutes(10),
                now,
                now
        );
    }
}
