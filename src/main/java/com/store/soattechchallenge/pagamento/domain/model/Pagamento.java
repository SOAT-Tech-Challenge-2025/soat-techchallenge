package com.store.soattechchallenge.pagamento.domain.model;

import com.store.soattechchallenge.pagamento.domain.exception.PagamentoAlreadyFinalizedException;

import java.time.LocalDateTime;

public class Pagamento {
    private String id;
    private String idExterno;
    private StatusPagamento stPagamento;
    private Double vlTotalPedido;
    private String codigoQr;
    private LocalDateTime expiracao;
    private LocalDateTime dtInclusao;
    private LocalDateTime timestamp;

    public Pagamento(
            String id,
            String idExterno,
            StatusPagamento stPagamento,
            Double vlTotalPedido,
            String codigoQr,
            LocalDateTime expiracao,
            LocalDateTime dtInclusao,
            LocalDateTime timestamp
    ) {
        this.id = id;
        this.idExterno = idExterno;
        this.stPagamento = stPagamento;
        this.vlTotalPedido = vlTotalPedido;
        this.codigoQr = codigoQr;
        this.expiracao = expiracao;
        this.dtInclusao = dtInclusao;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getIdExterno() {
        return idExterno;
    }

    public StatusPagamento getStPagamento() {
        return stPagamento;
    }

    public Double getVlTotalPedido() {
        return vlTotalPedido;
    }

    public String getCodigoQr() {
        return codigoQr;
    }

    public LocalDateTime getExpiracao() {
        return expiracao;
    }

    public LocalDateTime getDtInclusao() {
        return dtInclusao;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setIdExterno(String idExterno) {
        this.idExterno = idExterno;
    }

    public void setCodigoQr(String codigoQr) {
        this.codigoQr = codigoQr;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void finalize(StatusPagamento statusPagamento) {
        this.checkIfIsValidToFinalize(statusPagamento);
        this.stPagamento = statusPagamento;
        this.timestamp = LocalDateTime.now();
    }

    private void checkIfIsValidToFinalize(StatusPagamento newStatusPagamento) {
        boolean isValidStatus = this.stPagamento.equals(StatusPagamento.OPENED);
        boolean isValidNewStatus = !newStatusPagamento.equals(StatusPagamento.OPENED);
        if (!isValidStatus || !isValidNewStatus) {
            throw new PagamentoAlreadyFinalizedException(
                    "Unable to update a payment status from " + this.stPagamento + " to " + newStatusPagamento
            );
        }
    }
}
