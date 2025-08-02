package com.store.soattechchallenge.shoppingCart.order.gateways;

import com.store.soattechchallenge.shoppingCart.order.domain.events.DomainEvent;

public interface EventPublisherGateway {
    void publish(DomainEvent event);
}
