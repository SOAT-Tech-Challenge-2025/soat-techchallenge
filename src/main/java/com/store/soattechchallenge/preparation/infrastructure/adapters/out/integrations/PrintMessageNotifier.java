package com.store.soattechchallenge.preparation.infrastructure.adapters.out.integrations;

import com.store.soattechchallenge.preparation.domain.Notifier;
import com.store.soattechchallenge.preparation.domain.model.NotificationDestination;
import com.store.soattechchallenge.preparation.domain.model.NotificationMessage;
import org.springframework.stereotype.Component;

@Component
public class PrintMessageNotifier implements Notifier {

    @Override
    public void send(NotificationDestination destination, NotificationMessage message) {
        System.out.println("#####################");
        System.out.println("Notification sent");
        System.out.println("Email: " + destination.email());
        System.out.println("Title: " + message.title());
        System.out.println("Message: " + message.message());
        System.out.println("#####################");
    }
}