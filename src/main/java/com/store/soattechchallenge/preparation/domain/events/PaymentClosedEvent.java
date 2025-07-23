package com.store.soattechchallenge.preparation.domain.events;

public class PaymentClosedEvent {
    private final String paymentId;

    public PaymentClosedEvent(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentId() {
        return paymentId;
    }
}
