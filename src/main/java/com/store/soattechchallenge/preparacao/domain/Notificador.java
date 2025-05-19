package com.store.soattechchallenge.preparacao.domain;

import com.store.soattechchallenge.preparacao.domain.model.DestinoNotificacao;
import com.store.soattechchallenge.preparacao.domain.model.MensagemNotificacao;

public interface Notificador {
    public void send(DestinoNotificacao destino, MensagemNotificacao mensagem);
}