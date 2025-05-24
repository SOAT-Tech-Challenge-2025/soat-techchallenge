package com.store.soattechchallenge.pagamento.domain.exception;

public class PagamentoAlreadyFinalizedException extends RuntimeException {
    public PagamentoAlreadyFinalizedException(String message) { super(message); }
}
