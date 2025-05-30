package com.store.soattechchallenge.preparation.domain;

import com.store.soattechchallenge.preparation.domain.model.NotificationDestination;
import com.store.soattechchallenge.preparation.domain.model.NotificationMessage;

public interface Notifier {
    public void send(NotificationDestination destination, NotificationMessage message);
}