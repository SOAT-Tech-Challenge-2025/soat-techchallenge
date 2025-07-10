package com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.model;

public record MPPayment(MPPaymentOrder order, String status) {
}
