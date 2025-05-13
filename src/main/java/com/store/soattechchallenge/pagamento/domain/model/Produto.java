package com.store.soattechchallenge.pagamento.domain.model;

public record Produto(
        String nome,
        String categoria,
        Double valorPorUnidade,
        Integer quantidade
) {
}
