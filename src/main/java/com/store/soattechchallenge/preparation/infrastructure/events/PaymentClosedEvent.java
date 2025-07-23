package com.store.soattechchallenge.preparation.infrastructure.events;

public class PaymentClosedEvent {
    private final String paymentId;

    public PaymentClosedEvent(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentId() {
        return paymentId;
    }
}
