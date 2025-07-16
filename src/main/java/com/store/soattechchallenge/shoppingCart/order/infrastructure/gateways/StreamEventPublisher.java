package com.store.soattechchallenge.shoppingCart.order.infrastructure.gateways;

import com.store.soattechchallenge.shoppingCart.order.application.gateways.EventPublisher;
import com.store.soattechchallenge.shoppingCart.order.domain.events.DomainEvent;
import org.springframework.cloud.stream.function.StreamBridge;

public class StreamEventPublisher implements EventPublisher {
    private final StreamBridge streamBridge;

    public StreamEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void publish(DomainEvent event) {
        this.streamBridge.send("orderProducer-out-0", event);
    }
}
