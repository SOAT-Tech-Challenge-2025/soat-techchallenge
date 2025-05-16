package com.store.soattechchallenge.pagamento.domain.exceptions;

public class FinalizePagamentoError extends RuntimeException {
    public FinalizePagamentoError(String message) {
        super(message);
    }
}
