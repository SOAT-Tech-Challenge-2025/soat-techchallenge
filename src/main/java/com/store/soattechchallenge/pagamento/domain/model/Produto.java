package com.store.soattechchallenge.pagamento.domain.model;

public class Produto {
    private String nome;
    private String categoria;
    private Double valorPorUnidade;
    private Integer quantidade;

    public Produto (String nome, String categoria, Double valorPorUnidade, Integer quantidade) {
        this.nome = nome;
        this.categoria = categoria;
        this.valorPorUnidade = valorPorUnidade;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public Double getValorPorUnidade() {
        return valorPorUnidade;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getValorTotal() {
        return valorPorUnidade * quantidade;
    }
}
