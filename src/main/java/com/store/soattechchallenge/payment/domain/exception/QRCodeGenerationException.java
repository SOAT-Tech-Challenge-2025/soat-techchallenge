package com.store.soattechchallenge.payment.domain.exception;

public class QRCodeGenerationException extends RuntimeException {
    public QRCodeGenerationException(String message) {
        super(message);
    }
}
