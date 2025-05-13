package com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto;

import com.store.soattechchallenge.pagamento.domain.model.Produto;

import java.util.List;

public record PagamentoCreateRequestDTO(
        String id, Double vlTotalPedido, List<Produto> produtos
) {
}
