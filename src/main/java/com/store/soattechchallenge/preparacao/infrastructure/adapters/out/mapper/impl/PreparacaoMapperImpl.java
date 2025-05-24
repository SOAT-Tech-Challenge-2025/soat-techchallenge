package com.store.soattechchallenge.preparacao.infrastructure.adapters.out.mapper.impl;

import com.store.soattechchallenge.preparacao.domain.model.Preparacao;
import com.store.soattechchallenge.preparacao.domain.model.StatusPreparacao;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.in.dto.PreparacaoResponseDTO;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.model.JpaPreparacao;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.mapper.PreparacaoMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PreparacaoMapperImpl implements PreparacaoMapper {
    @Override
    public Preparacao toDomain(JpaPreparacao jpaPreparacao) {
        return new Preparacao(
                jpaPreparacao.getId(),
                jpaPreparacao.getPosicaoPreparacao(),
                jpaPreparacao.getTempoDePreparacao(),
                jpaPreparacao.getEstimativaDePronto(),
                StatusPreparacao.valueOf(jpaPreparacao.getStPreparacao()),
                jpaPreparacao.getDtInclusao(),
                jpaPreparacao.getTimestamp()
        );
    }

    @Override
    public PreparacaoResponseDTO toDTO(Preparacao preparacao) {
        return new PreparacaoResponseDTO(
                preparacao.getId(),
                Optional.ofNullable(preparacao.getPosicaoPreparacao()),
                preparacao.getTempoDePreparacao(),
                Optional.ofNullable(preparacao.getEstimativaDePronto()),
                preparacao.getStPreparacao(),
                preparacao.getDtInclusao(),
                preparacao.getTimestamp()
        );
    }

    @Override
    public JpaPreparacao toJPA(Preparacao preparacao) {
        return new JpaPreparacao(preparacao);
    }
}
