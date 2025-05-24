package com.store.soattechchallenge.preparacao.domain.exception;

public class PreparacaoAlreadyExistsException extends RuntimeException {
    public PreparacaoAlreadyExistsException(String message) {
        super(message);
    }
}
