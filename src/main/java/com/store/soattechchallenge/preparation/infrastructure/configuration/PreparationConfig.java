package com.store.soattechchallenge.preparation.infrastructure.configuration;

import com.store.soattechchallenge.preparation.infrastructure.events.PaymentClosedEvent;
import com.store.soattechchallenge.preparation.infrastructure.events.PaymentClosedHandler;
import com.store.soattechchallenge.preparation.infrastructure.jpa.PreparationJpaRepository;
import com.store.soattechchallenge.preparation.infrastructure.mappers.PreparationMapper;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.OrderAdaptersGetRepository;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.OrderMapper;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PreparationConfig {
    @Bean
    PreparationMapper preparationMapper() {
        return new PreparationMapper();
    }

    @Bean
    Consumer<PaymentClosedEvent> consumePaymentClosedEvent (
            PreparationJpaRepository preparationJpaRepository,
            PreparationMapper preparationMapper,
            OrderAdaptersGetRepository orderJpaRepository,
            OrderMapper orderMapper,
            StreamBridge streamBridge
    ) {
        PaymentClosedHandler handler = new PaymentClosedHandler(
                preparationJpaRepository, preparationMapper, orderJpaRepository, orderMapper, streamBridge
        );

        return handler::handle;
    }
}
