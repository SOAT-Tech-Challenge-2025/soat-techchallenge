package com.store.soattechchallenge.payment.domain.events;

public class PaymentClosedEvent extends DomainEvent {
    private final String paymentId;

    public PaymentClosedEvent(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    @Override
    public String eventName() {
        return "payment.closed";
    }
}
