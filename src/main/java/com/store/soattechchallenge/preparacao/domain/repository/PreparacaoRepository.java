package com.store.soattechchallenge.preparacao.domain.repository;

import com.store.soattechchallenge.preparacao.domain.model.Preparacao;

//import java.util.List;

public interface PreparacaoRepository {
    public Preparacao save(Preparacao preparacao);
    public Boolean existsById(String id);
    public Integer findMaxPosicao();
//    public List<Preparacao> getWaitingList();
//    public Preparacao findNext();
//    public Preparacao save(Preparacao preparacao);
}