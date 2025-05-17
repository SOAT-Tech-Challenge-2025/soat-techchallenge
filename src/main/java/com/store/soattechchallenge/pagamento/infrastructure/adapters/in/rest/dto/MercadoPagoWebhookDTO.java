package com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto;

public record MercadoPagoWebhookDTO (
        String action,
        MercadoPagoWebhookDataDTO data,
        String type
) {
}
