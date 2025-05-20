package com.store.soattechchallenge.preparacao.domain.repository;

import com.store.soattechchallenge.preparacao.domain.model.Preparacao;

import java.util.List;
import java.util.Optional;

//import java.util.List;

public interface PreparacaoRepository {
    public Preparacao save(Preparacao preparacao);
    public Preparacao findById(String id);
    public Boolean existsById(String id);
    public Integer findMaxPosicao();
    public Optional<Preparacao> findRecebidoWithMinPosicao();
    public void decrementRecebidoPosicoesGreaterThan(Integer posicaoPreparacao);
    public List<Preparacao> getRecebidoWaitingList();
    public List<Preparacao> getEmPreparacaoWaitingList();
    public List<Preparacao> getReadyWaitingList();
}