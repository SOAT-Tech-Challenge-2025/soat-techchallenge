package com.store.soattechchallenge.payment.infrastructure.adapters.out.repository.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
