package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.pagamento.domain.exceptions.NotFound;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.domain.repository.PagamentoRepository;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.model.JPAPagamento;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.repository.PagamentoJPARepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PagamentoRepositoryImpl implements PagamentoRepository {
    private final PagamentoJPARepository jpaRepository;

    public PagamentoRepositoryImpl (PagamentoJPARepository pagamentoJPARepository) {
        this.jpaRepository = pagamentoJPARepository;
    }

    @Override
    public Pagamento findById(String id) {
        Optional<JPAPagamento> optionalJPAPagamento = this.jpaRepository.findById(id);
        if (optionalJPAPagamento.isEmpty()) {
            throw new NotFound("Pagamento não encontrado");
        }

        JPAPagamento jpaPagamento = optionalJPAPagamento.get();
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
    public Pagamento findByIdExterno(String idExterno) {
        Optional<JPAPagamento> optionalJPAPagamento = this.jpaRepository.findByIdExterno(idExterno);
        if (optionalJPAPagamento.isEmpty()) {
            throw new NotFound("Pagamento não encontrado");
        }

        JPAPagamento jpaPagamento = optionalJPAPagamento.get();
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
    public Pagamento save(Pagamento pagamento) {
        JPAPagamento jpaPagamento = new JPAPagamento(pagamento);
        jpaPagamento = this.jpaRepository.save(jpaPagamento);
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
}
