package com.store.soattechchallenge.payment.domain.exception;

public class PaymentAlreadyExists extends RuntimeException {
    public PaymentAlreadyExists(String message) { super(message); }
}
