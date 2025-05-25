package com.store.soattechchallenge.payment.domain.exception;

public class PaymentAlreadyFinalizedException extends RuntimeException {
    public PaymentAlreadyFinalizedException(String message) { super(message); }
}
