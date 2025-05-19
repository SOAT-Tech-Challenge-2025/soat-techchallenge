
package com.store.soattechchallenge.preparacao.application.usecases;

import com.store.soattechchallenge.preparacao.domain.model.Preparacao;

import java.util.List;

public interface PreparacaoUseCase {
    public Preparacao create(String id, Integer tempoDePreparacao);
//    public Preparacao startNext();
//    public Preparacao ready(String id);
//    public Preparacao finalize(String id);
//    public List<Preparacao> waitingList(String id);
}