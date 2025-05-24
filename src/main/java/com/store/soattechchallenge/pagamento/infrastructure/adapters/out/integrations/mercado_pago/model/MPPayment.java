package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model;

public record MPPayment(MPPaymentOrder order, String status) {
}
