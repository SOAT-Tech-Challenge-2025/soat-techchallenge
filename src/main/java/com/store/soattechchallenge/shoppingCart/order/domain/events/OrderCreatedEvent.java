package com.store.soattechchallenge.shoppingCart.order.domain.events;

public class OrderCreatedEvent extends DomainEvent {
    private final String orderId;

    public OrderCreatedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return this.orderId;
    }
}