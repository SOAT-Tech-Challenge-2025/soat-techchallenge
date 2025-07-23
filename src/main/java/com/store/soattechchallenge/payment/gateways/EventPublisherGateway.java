package com.store.soattechchallenge.payment.gateways;

import com.store.soattechchallenge.payment.domain.events.DomainEvent;

public interface EventPublisherGateway {
    void publish(DomainEvent event);
}
