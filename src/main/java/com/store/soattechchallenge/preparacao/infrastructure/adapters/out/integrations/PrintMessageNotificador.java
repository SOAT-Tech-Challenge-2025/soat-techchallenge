package com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations;

import com.store.soattechchallenge.preparacao.domain.Notificador;
import com.store.soattechchallenge.preparacao.domain.model.DestinoNotificacao;
import com.store.soattechchallenge.preparacao.domain.model.MensagemNotificacao;
import org.springframework.stereotype.Component;

@Component
public class PrintMessageNotificador implements Notificador {

    @Override
    public void send(DestinoNotificacao destino, MensagemNotificacao mensagem) {
        System.out.println("#####################");
        System.out.println("Notificação enviada");
        System.out.println("Email: " + destino.email());
        System.out.println("Título: " + mensagem.title());
        System.out.println("Mensagem: " + mensagem.message());
        System.out.println("#####################");
    }
}