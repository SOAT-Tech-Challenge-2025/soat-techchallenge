package com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.model;

public record MPItem(
        String title,
        String category,
        Integer quantity,
        String unitMeasure,
        Double unitPrice,
        Double totalAmount
) {
}
