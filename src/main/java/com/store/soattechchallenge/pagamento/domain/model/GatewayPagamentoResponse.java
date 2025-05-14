package com.store.soattechchallenge.pagamento.domain.model;

public class GatewayPagamentoResponse {
    private String id;
    private String codigoQr;

    public GatewayPagamentoResponse(String id, String codigoQr) {
        this.id = id;
        this.codigoQr = codigoQr;
    }

    public String getId() {
        return id;
    }

    public String getCodigoQr() {
        return codigoQr;
    }
}
