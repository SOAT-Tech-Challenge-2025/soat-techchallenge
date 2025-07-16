package com.store.soattechchallenge.shoppingCart.order.domain.events;

import java.time.LocalDateTime;

public abstract class DomainEvent {
    private final LocalDateTime createdAt = LocalDateTime.now();

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public abstract String eventName ();
}
