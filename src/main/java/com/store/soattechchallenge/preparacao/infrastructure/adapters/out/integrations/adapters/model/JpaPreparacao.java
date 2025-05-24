package com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.model;

import com.store.soattechchallenge.preparacao.domain.model.Preparacao;
import com.store.soattechchallenge.preparacao.domain.model.StatusPreparacao;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_preparacao")
public class JpaPreparacao {
    @Id
    private String id;
    private Integer posicaoPreparacao;
    private Integer tempoDePreparacao;
    private LocalDateTime estimativaDePronto;
    private String stPreparacao;
    private LocalDateTime dtInclusao;
    private LocalDateTime timestamp;

    public JpaPreparacao() {
    }

    public JpaPreparacao(Preparacao preparacao) {
        this.id = preparacao.getId();
        this.posicaoPreparacao = preparacao.getPosicaoPreparacao();
        this.tempoDePreparacao = preparacao.getTempoDePreparacao();
        this.estimativaDePronto = preparacao.getEstimativaDePronto();
        this.stPreparacao = preparacao.getStPreparacao().toString();
        this.dtInclusao = preparacao.getDtInclusao();
        this.timestamp = preparacao.getTimestamp();
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

    public String getStPreparacao() {
        return stPreparacao;
    }

    public LocalDateTime getDtInclusao() {
        return dtInclusao;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
