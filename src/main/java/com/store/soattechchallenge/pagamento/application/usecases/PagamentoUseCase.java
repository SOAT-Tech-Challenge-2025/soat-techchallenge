package com.store.soattechchallenge.pagamento.application.usecases;

import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.domain.model.Produto;

import java.util.List;

public interface PagamentoUseCase {
    public Pagamento find (String id);
    public Pagamento create (String id, Double vlTotalPedido, List<Produto> produtos);
    public Pagamento finalizeByMercadoPagoPaymentId (String paymentId);
}
