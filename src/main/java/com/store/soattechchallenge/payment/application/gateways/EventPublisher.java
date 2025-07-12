package com.store.soattechchallenge.payment.application.gateways;

import com.store.soattechchallenge.payment.domain.events.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}
