package com.store.soattechchallenge.payment.infrastructure.gateways;

import com.store.soattechchallenge.payment.domain.events.DomainEvent;
import com.store.soattechchallenge.payment.gateways.EventPublisherGateway;
import org.springframework.cloud.stream.function.StreamBridge;

public class StreamEventPublisherGateway implements EventPublisherGateway {
    private final StreamBridge streamBridge;

    public StreamEventPublisherGateway(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void publish(DomainEvent event) {
        this.streamBridge.send("paymentProducer-out-0", event);
    }
}
