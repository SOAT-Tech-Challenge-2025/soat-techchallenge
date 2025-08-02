package com.store.soattechchallenge.payment.infrastructure.events;

public class OrderCreatedEvent {
    private final String orderId;

    public OrderCreatedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return this.orderId;
    }
}
