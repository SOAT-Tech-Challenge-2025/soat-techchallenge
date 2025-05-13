package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.model;

import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.domain.model.StatusPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pagamento")
public class JPAPagamento {
    @Id
    private String id;

    @Column(unique = true)
    private String idExterno;

    private StatusPagamento stPagamento;

    private Double vlTotalPedido;
    private String codigoQr;
    private LocalDateTime expiracao;

    private LocalDateTime dtInclusao;

    private LocalDateTime timestamp;

    public JPAPagamento () {

    }

    public JPAPagamento (Pagamento pagamento) {
        this.id = pagamento.id();
        this.idExterno = pagamento.idExterno();
        this.stPagamento = pagamento.stPagamento();
        this.vlTotalPedido = pagamento.vlTotalPedido();
        this.codigoQr = pagamento.codigoQr();
        this.expiracao = pagamento.expiracao();
        this.dtInclusao = pagamento.dtInclusao();
        this.timestamp = pagamento.timestamp();
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
}
