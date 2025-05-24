package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers.impl;

import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.PagamentoResponseDTO;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers.PagamentoMapper;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.model.JPAPagamento;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapperImpl implements PagamentoMapper {

    @Override
    public Pagamento toDomain(JPAPagamento jpaPagamento) {
        return new Pagamento(
                jpaPagamento.getId(),
                jpaPagamento.getIdExterno(),
                jpaPagamento.getStPagamento(),
                jpaPagamento.getVlTotalPedido(),
                jpaPagamento.getCodigoQr(),
                jpaPagamento.getExpiracao(),
                jpaPagamento.getDtInclusao(),
                jpaPagamento.getTimestamp()
        );
    }

    @Override
    public PagamentoResponseDTO toDTO(Pagamento pagamento) {
        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getIdExterno(),
                pagamento.getStPagamento(),
                pagamento.getVlTotalPedido(),
                pagamento.getCodigoQr(),
                pagamento.getExpiracao(),
                pagamento.getDtInclusao(),
                pagamento.getTimestamp()
        );
    }

    @Override
    public JPAPagamento toJPA(Pagamento pagamento) {
        return new JPAPagamento(pagamento);
    }
}
