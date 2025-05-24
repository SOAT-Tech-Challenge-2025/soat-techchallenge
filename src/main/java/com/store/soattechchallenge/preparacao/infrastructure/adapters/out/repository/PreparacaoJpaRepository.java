package com.store.soattechchallenge.preparacao.infrastructure.adapters.out.repository;

import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.model.JpaPreparacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PreparacaoJpaRepository extends JpaRepository<JpaPreparacao, String> {
    Optional<JpaPreparacao> findFirstByStPreparacaoOrderByPosicaoPreparacaoDesc(String stPreparacao);
    Optional<JpaPreparacao> findFirstByStPreparacaoOrderByPosicaoPreparacaoAsc(String stPreparacao);
    List<JpaPreparacao> findByStPreparacaoOrderByPosicaoPreparacaoAsc(String stPreparacao);
    List<JpaPreparacao> findByStPreparacaoOrderByEstimativaDeProntoAsc(String stPreparacao);
    List<JpaPreparacao> findByStPreparacaoOrderByTimestampAsc(String stPreparacao);
    @Modifying
    @Query("UPDATE JpaPreparacao p SET p.posicaoPreparacao = p.posicaoPreparacao - 1 " +
            "WHERE p.stPreparacao = 'RECEBIDO' AND p.posicaoPreparacao > :posicaoPreparacao")
    void decrementRecebidoPosicoesGreaterThan(@Param("posicaoPreparacao") Integer posicaoPreparacao);
}
