package com.store.soattechchallenge.pagamento.domain.exceptions;

public class PagamentoAlreadyFinalized extends RuntimeException {
    public PagamentoAlreadyFinalized(String message) { super(message); }
}
