package com.store.soattechchallenge.pagamento.domain.exception;

public class PagamentoAlreadyExists extends RuntimeException {
    public PagamentoAlreadyExists(String message) { super(message); }
}
