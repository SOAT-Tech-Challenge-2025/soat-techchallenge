package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.exceptions;

public class MPNotFoundError extends MPClientError {
    public MPNotFoundError(String message) {
        super(message);
    }
}
