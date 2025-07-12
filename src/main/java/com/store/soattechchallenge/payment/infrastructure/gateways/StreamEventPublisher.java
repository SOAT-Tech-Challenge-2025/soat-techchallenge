package com.store.soattechchallenge.payment.infrastructure.gateways;

import org.springframework.cloud.stream.function.StreamBridge;
import com.store.soattechchallenge.payment.application.gateways.EventPublisher;
import com.store.soattechchallenge.payment.domain.events.DomainEvent;

public class StreamEventPublisher implements EventPublisher {
    private final StreamBridge streamBridge;

    public StreamEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void publish(DomainEvent event) {
        this.streamBridge.send("paymentProducer-out-0", event);
    }
}
