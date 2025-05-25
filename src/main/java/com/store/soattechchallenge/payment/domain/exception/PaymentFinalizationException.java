package com.store.soattechchallenge.payment.domain.exception;

public class PaymentFinalizationException extends RuntimeException {
    public PaymentFinalizationException(String message) {
        super(message);
    }
}
