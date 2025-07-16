package com.store.soattechchallenge.shoppingCart.order.application.gateways;

import com.store.soattechchallenge.shoppingCart.order.domain.events.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}
