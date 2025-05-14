package com.store.soattechchallenge.pagamento.domain.exceptions;

public class AlreadyExists extends RuntimeException {
    public AlreadyExists(String message) { super(message); }
}
