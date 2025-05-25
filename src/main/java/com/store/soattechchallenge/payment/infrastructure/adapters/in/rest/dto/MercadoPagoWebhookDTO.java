package com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto;

public record MercadoPagoWebhookDTO (
        String action,
        MercadoPagoWebhookDataDTO data,
        String type
) {
}
