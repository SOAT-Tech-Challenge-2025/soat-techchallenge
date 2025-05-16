package com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto;

import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPOrderStatus;

public record MercadoPagoWebhookDTO (
        String action,
        MercadoPagoWebhookDataDTO data,
        String type
) {
}
