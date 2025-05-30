package com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.model;

public record MPPayment(MPPaymentOrder order, String status) {
}
