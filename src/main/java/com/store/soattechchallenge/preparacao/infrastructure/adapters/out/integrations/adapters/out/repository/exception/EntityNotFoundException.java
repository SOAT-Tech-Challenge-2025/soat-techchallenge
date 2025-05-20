package com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.out.repository.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
