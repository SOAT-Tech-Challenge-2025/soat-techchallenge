package com.store.soattechchallenge.pagamento.domain.exceptions;

public class GenerateCodigoQRError extends RuntimeException {
    public GenerateCodigoQRError(String message) {
        super(message);
    }
}
