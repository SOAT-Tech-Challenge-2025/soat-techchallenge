package com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.model;

import java.util.List;

public record MPCreateOrderRequest(
        String externalReference,
        Double totalAmount,
        String title,
        String description,
        String expirationDate,
        List<MPItem> items,
        String notificationUrl
) {
}
