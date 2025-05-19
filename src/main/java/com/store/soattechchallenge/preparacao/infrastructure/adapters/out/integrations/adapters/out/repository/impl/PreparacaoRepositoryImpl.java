package com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.out.repository.impl;

import com.store.soattechchallenge.preparacao.domain.model.Preparacao;
import com.store.soattechchallenge.preparacao.domain.model.StatusPreparacao;
import com.store.soattechchallenge.preparacao.domain.repository.PreparacaoRepository;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.model.JpaPreparacao;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.out.mapper.PreparacaoMapper;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.out.repository.PreparacaoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
}
