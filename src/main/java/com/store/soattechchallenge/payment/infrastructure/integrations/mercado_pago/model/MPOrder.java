package com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.model;

public record MPOrder(
        Long id,
        MPOrderStatus status,
        String externalReference
) {
}
