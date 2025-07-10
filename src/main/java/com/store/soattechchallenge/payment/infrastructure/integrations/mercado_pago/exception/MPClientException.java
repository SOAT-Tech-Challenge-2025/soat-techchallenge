package com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.exception;

public class MPClientException extends RuntimeException {
    public MPClientException(String message) {
        super(message);
    }
}
