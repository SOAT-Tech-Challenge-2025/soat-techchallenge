package com.store.soattechchallenge.preparacao.application.service;

import com.store.soattechchallenge.preparacao.application.usecases.PreparacaoUseCase;
import com.store.soattechchallenge.preparacao.domain.Notificador;
import com.store.soattechchallenge.preparacao.domain.exception.NoPreparacaoToStartException;
import com.store.soattechchallenge.preparacao.domain.exception.PreparacaoAlreadyExistsException;
import com.store.soattechchallenge.preparacao.domain.exception.InvalidPreparacaoException;
import com.store.soattechchallenge.preparacao.domain.model.DestinoNotificacao;
import com.store.soattechchallenge.preparacao.domain.model.MensagemNotificacao;
import com.store.soattechchallenge.preparacao.domain.model.Preparacao;
import com.store.soattechchallenge.preparacao.domain.model.StatusPreparacao;
import com.store.soattechchallenge.preparacao.domain.repository.PreparacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PreparacaoService implements PreparacaoUseCase {
    private PreparacaoRepository preparacaoRepository;
    private Notificador notificador;

    public PreparacaoService(PreparacaoRepository preparacaoRepository, Notificador notificador) {
        this.preparacaoRepository = preparacaoRepository;
        this.notificador = notificador;
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

    @Override
    public Preparacao startNext() {
        Optional<Preparacao> preparacaoOptional = this.preparacaoRepository.findRecebidoWithMinPosicao();
        if (preparacaoOptional.isEmpty()) {
            throw new NoPreparacaoToStartException("No preparation available to start");
        }

        Preparacao preparacao = preparacaoOptional.get();
        Integer oldPosicaoPreparacao = preparacao.getPosicaoPreparacao();
        preparacao.setPosicaoPreparacao(null);
        preparacao.setStPreparacao(StatusPreparacao.EM_PREPARACAO);
        preparacao.setEstimativaDePronto(LocalDateTime.now().plusSeconds(preparacao.getTempoDePreparacao()));
        preparacao.setTimestamp(LocalDateTime.now());
        preparacao = this.preparacaoRepository.save(preparacao);
        this.preparacaoRepository.decrementRecebidoPosicoesGreaterThan(oldPosicaoPreparacao);
        return preparacao;
    }

    @Override
    public Preparacao ready(String id) {
        Preparacao preparacao = this.preparacaoRepository.findById(id);
        if (!preparacao.getStPreparacao().equals(StatusPreparacao.EM_PREPARACAO)) {
            throw new InvalidPreparacaoException(
                    "A preparation with " + preparacao.getStPreparacao() + " status cannot be updated to " +
                            StatusPreparacao.PRONTO
            );
        }

        preparacao.setStPreparacao(StatusPreparacao.PRONTO);
        preparacao.setTimestamp(LocalDateTime.now());
        preparacao = this.preparacaoRepository.save(preparacao);
        DestinoNotificacao destinoNotificacao = new DestinoNotificacao("test@email.com");
        MensagemNotificacao mensagemNotificacao = new MensagemNotificacao(
                "Seu pedido está pronto",
                "O pedido " + preparacao.getId() + " está disponível no balcão"
        );
        this.notificador.send(destinoNotificacao, mensagemNotificacao);
        return preparacao;
    }

    @Override
    public Preparacao finalize(String id) {
        Preparacao preparacao = this.preparacaoRepository.findById(id);
        if (!preparacao.getStPreparacao().equals(StatusPreparacao.PRONTO)) {
            throw new InvalidPreparacaoException(
                    "A preparation with " + preparacao.getStPreparacao() + " status cannot be updated to " +
                            StatusPreparacao.FINALIZADO
            );
        }
        preparacao.setStPreparacao(StatusPreparacao.FINALIZADO);
        preparacao.setTimestamp(LocalDateTime.now());
        return this.preparacaoRepository.save(preparacao);
    }

    @Override
    public List<Preparacao> waitingList() {
        List<Preparacao> preparacoes = this.preparacaoRepository.getReadyWaitingList();
        preparacoes.addAll(this.preparacaoRepository.getEmPreparacaoWaitingList());
        preparacoes.addAll(this.preparacaoRepository.getRecebidoWaitingList());
        return preparacoes;
    }
}
