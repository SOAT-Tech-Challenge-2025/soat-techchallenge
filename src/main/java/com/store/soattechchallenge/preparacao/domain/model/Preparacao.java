package com.store.soattechchallenge.preparacao.domain.model;

import java.time.LocalDateTime;

public class Preparacao {
    private String id;
    private Integer posicaoPreparacao;
    private Integer tempoDePreparacao;
    private LocalDateTime estimativaDePronto;
    private StatusPreparacao stPreparacao;
    private LocalDateTime dtInclusao;
    private LocalDateTime timestamp;

    public Preparacao() {
        
    }

    public Preparacao(
            String id,
            Integer posicaoPreparacao,
            Integer tempoDePreparacao,
            LocalDateTime estimativaDePronto,
            StatusPreparacao stPreparacao,
            LocalDateTime dtInclusao,
            LocalDateTime timestamp
    ) {
        this.id = id;
        this.posicaoPreparacao = posicaoPreparacao;
        this.tempoDePreparacao = tempoDePreparacao;
        this.estimativaDePronto = estimativaDePronto;
        this.stPreparacao = stPreparacao;
        this.dtInclusao = dtInclusao;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public Integer getPosicaoPreparacao() {
        return posicaoPreparacao;
    }

    public Integer getTempoDePreparacao() {
        return tempoDePreparacao;
    }

    public LocalDateTime getEstimativaDePronto() {
        return estimativaDePronto;
    }

    public StatusPreparacao getStPreparacao() {
        return stPreparacao;
    }

    public LocalDateTime getDtInclusao() {
        return dtInclusao;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setPosicaoPreparacao(Integer posicaoPreparacao) {
        this.posicaoPreparacao = posicaoPreparacao;
    }

    public void setStPreparacao(StatusPreparacao stPreparacao) {
        this.stPreparacao = stPreparacao;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setEstimativaDePronto(LocalDateTime estimativaDePronto) {
        this.estimativaDePronto = estimativaDePronto;
    }
}
