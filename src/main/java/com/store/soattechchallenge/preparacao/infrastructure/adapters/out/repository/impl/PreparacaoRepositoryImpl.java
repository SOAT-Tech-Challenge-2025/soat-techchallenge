package com.store.soattechchallenge.preparacao.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.preparacao.domain.model.Preparacao;
import com.store.soattechchallenge.preparacao.domain.model.StatusPreparacao;
import com.store.soattechchallenge.preparacao.domain.repository.PreparacaoRepository;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.model.JpaPreparacao;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.mapper.PreparacaoMapper;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.repository.PreparacaoJpaRepository;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.repository.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PreparacaoRepositoryImpl implements PreparacaoRepository {
    PreparacaoJpaRepository preparacaoJpaRepository;
    PreparacaoMapper preparacaoMapper;

    public PreparacaoRepositoryImpl(
            PreparacaoJpaRepository preparacaoJpaRepository,
            PreparacaoMapper preparacaoMapper
    ) {
        this.preparacaoJpaRepository = preparacaoJpaRepository;
        this.preparacaoMapper = preparacaoMapper;
    }

    @Override
    public Preparacao save(Preparacao preparacao) {
        JpaPreparacao jpaPreparacao = this.preparacaoJpaRepository.save(this.preparacaoMapper.toJPA(preparacao));
        return this.preparacaoMapper.toDomain(jpaPreparacao);
    }

    @Override
    public Preparacao findById(String id) {
        Optional<JpaPreparacao> jpaPreparacaoOptional = this.preparacaoJpaRepository.findById(id);
        if (jpaPreparacaoOptional.isEmpty()) {
            throw new EntityNotFoundException("Preparation with ID " + id + " not found");
        }

        return this.preparacaoMapper.toDomain(jpaPreparacaoOptional.get());
    }

    @Override
    public Boolean existsById(String id) {
        return this.preparacaoJpaRepository.existsById(id);
    }

    @Override
    public Integer findMaxPosicao() {
        Optional<JpaPreparacao>jpaPreparacaoOptional = this.preparacaoJpaRepository
                .findFirstByStPreparacaoOrderByPosicaoPreparacaoDesc(StatusPreparacao.RECEBIDO.toString());

        Integer maxPosicao = 0;
        if (jpaPreparacaoOptional.isPresent()) {
            maxPosicao = jpaPreparacaoOptional.get().getPosicaoPreparacao();
        }

        return maxPosicao != null ? maxPosicao : 0;
    }

    @Override
    public Optional<Preparacao> findRecebidoWithMinPosicao() {
        Optional<JpaPreparacao>jpaPreparacaoOptional = this.preparacaoJpaRepository
                .findFirstByStPreparacaoOrderByPosicaoPreparacaoAsc(StatusPreparacao.RECEBIDO.toString());

        return jpaPreparacaoOptional.map(jpaPreparacao -> this.preparacaoMapper.toDomain(jpaPreparacao));

    }

    @Override
    public void decrementRecebidoPosicoesGreaterThan(Integer posicaoPreparacao) {
        this.preparacaoJpaRepository.decrementRecebidoPosicoesGreaterThan(posicaoPreparacao);
    }

    @Override
    public List<Preparacao> getRecebidoWaitingList() {
        return this.preparacaoJpaRepository
                .findByStPreparacaoOrderByPosicaoPreparacaoAsc(StatusPreparacao.RECEBIDO.toString())
                .stream()
                .map(this.preparacaoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Preparacao> getEmPreparacaoWaitingList() {
        return this.preparacaoJpaRepository.findByStPreparacaoOrderByEstimativaDeProntoAsc(
                    StatusPreparacao.EM_PREPARACAO.toString()
                )
                .stream()
                .map(this.preparacaoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Preparacao> getReadyWaitingList() {
        return this.preparacaoJpaRepository.findByStPreparacaoOrderByTimestampAsc(
                        StatusPreparacao.PRONTO.toString()
                )
                .stream()
                .map(this.preparacaoMapper::toDomain)
                .collect(Collectors.toList());
    }
}
