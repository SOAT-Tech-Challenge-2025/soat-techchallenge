package com.store.soattechchallenge.payment.infrastructure.api.dto;

public record MercadoPagoWebhookDTO (
        String action,
        MercadoPagoWebhookDataDTO data,
        String type
) {
}
