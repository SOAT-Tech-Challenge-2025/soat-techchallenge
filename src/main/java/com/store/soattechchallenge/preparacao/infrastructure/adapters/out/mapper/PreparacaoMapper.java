package com.store.soattechchallenge.preparacao.infrastructure.adapters.out.mapper;

import com.store.soattechchallenge.preparacao.domain.model.Preparacao;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.in.dto.PreparacaoResponseDTO;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.model.JpaPreparacao;

public interface PreparacaoMapper {
    public Preparacao toDomain(JpaPreparacao jpaPreparacao);
    public PreparacaoResponseDTO toDTO (Preparacao preparacao);
    public JpaPreparacao toJPA(Preparacao preparacao);
}
