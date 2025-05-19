package com.store.soattechchallenge.preparacao.application.usecases;

import com.store.soattechchallenge.preparacao.domain.exception.PreparacaoAlreadyExistsException;
import com.store.soattechchallenge.preparacao.domain.model.Preparacao;
import com.store.soattechchallenge.preparacao.domain.model.StatusPreparacao;
import com.store.soattechchallenge.preparacao.domain.repository.PreparacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PreparacaoService implements PreparacaoUseCase {
    private PreparacaoRepository preparacaoRepository;

    public PreparacaoService(PreparacaoRepository preparacaoRepository) {
        this.preparacaoRepository = preparacaoRepository;
    }

    @Override
    public Preparacao create(String id, Integer tempoDePreparacao) {
        if (this.preparacaoRepository.existsById(id)) {
            throw new PreparacaoAlreadyExistsException("Preparation with ID " + id + " already exists");
        }

        Integer maxPosicao = this.preparacaoRepository.findMaxPosicao();
        LocalDateTime now = LocalDateTime.now();
        Preparacao preparacao = new Preparacao(
                id,
                maxPosicao + 1,
                tempoDePreparacao,
                null,
                StatusPreparacao.RECEBIDO,
                now,
                now
        );

        return this.preparacaoRepository.save(preparacao);
    }
}
