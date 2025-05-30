package com.store.soattechchallenge.preparation.infrastructure.adapters.out.repository.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
