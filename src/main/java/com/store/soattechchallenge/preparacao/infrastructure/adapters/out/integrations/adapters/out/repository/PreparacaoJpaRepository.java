package com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.out.repository;

import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.model.JpaPreparacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreparacaoJpaRepository extends JpaRepository<JpaPreparacao, String> {
    Optional<JpaPreparacao> findFirstByStPreparacaoOrderByPosicaoPreparacaoDesc(String stPreparacao);
}
