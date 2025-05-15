package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.exceptions;

public class MPClientError extends RuntimeException {
    public MPClientError(String message) {
        super(message);
    }
}
