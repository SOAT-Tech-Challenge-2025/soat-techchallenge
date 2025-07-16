package com.store.soattechchallenge.payment.domain.events;

import com.store.soattechchallenge.payment.domain.events.DomainEvent;

public class OrderCreatedEvent extends DomainEvent {
    private final String orderId;

    public OrderCreatedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    @Override
    public String eventName() {
        return "order.created";
    }
}
