package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.exception;

public class MPNotFoundException extends MPClientException {
    public MPNotFoundException(String message) {
        super(message);
    }
}
