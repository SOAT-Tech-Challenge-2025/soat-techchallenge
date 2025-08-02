package com.store.soattechchallenge.shoppingCart.order.infrastructure.gateways;

import com.store.soattechchallenge.shoppingCart.order.domain.events.DomainEvent;
import com.store.soattechchallenge.shoppingCart.order.gateways.EventPublisherGateway;
import org.springframework.cloud.stream.function.StreamBridge;

public class StreamEventPublisherGateway implements EventPublisherGateway {
    private final StreamBridge streamBridge;

    public StreamEventPublisherGateway(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void publish(DomainEvent event) {
        this.streamBridge.send("orderProducer-out-0", event);
    }
}
