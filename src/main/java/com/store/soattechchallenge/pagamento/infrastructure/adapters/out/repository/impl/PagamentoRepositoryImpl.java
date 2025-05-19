package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.repository.exception.EntityNotFoundException;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.domain.repository.PagamentoRepository;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers.PagamentoMapper;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.model.JPAPagamento;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.repository.PagamentoJPARepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PagamentoRepositoryImpl implements PagamentoRepository {
    private final PagamentoJPARepository jpaRepository;
    private final PagamentoMapper pagamentoMapper;

    public PagamentoRepositoryImpl (
            PagamentoJPARepository pagamentoJPARepository,
            PagamentoMapper pagamentoMapper
    ) {
        this.jpaRepository = pagamentoJPARepository;
        this.pagamentoMapper = pagamentoMapper;
    }

    @Override
    public Pagamento findById(String id) {
        Optional<JPAPagamento> optionalJPAPagamento = this.jpaRepository.findById(id);
        if (optionalJPAPagamento.isEmpty()) {
            throw new EntityNotFoundException("Payment with ID " + id + " not found");
        }

        return this.pagamentoMapper.toDomain(optionalJPAPagamento.get());
    }

    @Override
    public Boolean existsById(String id) {
        return this.jpaRepository.existsById(id);
    }

    @Override
    public Boolean existsByIdExterno(String id) {
        return this.jpaRepository.existsByIdExterno(id);
    }

    @Override
    public Pagamento save(Pagamento pagamento) {
        JPAPagamento jpaPagamento = this.jpaRepository.save(
                this.pagamentoMapper.toJPA(pagamento)
        );

        return this.pagamentoMapper.toDomain(jpaPagamento);
    }
}
