package com.store.soattechchallenge.payment.infrastructure.configuration;

import com.google.zxing.qrcode.QRCodeWriter;
import com.store.soattechchallenge.payment.infrastructure.api.validator.MercadoPagoWebhookValidator;
import com.store.soattechchallenge.payment.infrastructure.events.OrderCreatedEvent;
import com.store.soattechchallenge.payment.infrastructure.events.OrderCreatedHandler;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.MercadoPagoClient;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.impl.MercadoPagoClientImpl;
import com.store.soattechchallenge.payment.infrastructure.jpa.PaymentJpaRepository;
import com.store.soattechchallenge.payment.infrastructure.mappers.PaymentMapper;
import com.store.soattechchallenge.payment.infrastructure.mappers.PaymentProductMapper;
import com.store.soattechchallenge.shoppingCart.order.gateways.OrderRepositoryGateways;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.OrderMapper;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PaymentConfig {
    @Bean
    PaymentMapper paymentMapper () {
        return new PaymentMapper();
    }

    @Bean
    PaymentProductMapper paymentProductMapper () {
        return new PaymentProductMapper();
    }

    @Bean
    QRCodeWriter qrCodeWriter () {
        return new QRCodeWriter();
    }

    @Bean
    MercadoPagoIntegrationConfig mercadoPagoIntegrationConfig () {
        return new MercadoPagoIntegrationConfig();
    }

    @Bean
    MercadoPagoClient mercadoPagoClient (MercadoPagoIntegrationConfig mercadoPagoIntegrationConfig) {
        return new MercadoPagoClientImpl(mercadoPagoIntegrationConfig);
    }

    @Bean
    MercadoPagoWebhookValidator mercadoPagoWebhookValidator (
            MercadoPagoIntegrationConfig mercadoPagoIntegrationConfig
    ) {
        return new MercadoPagoWebhookValidator(mercadoPagoIntegrationConfig);
    }

    @Bean
    Consumer<OrderCreatedEvent> consumeOrderCreatedEvent(
            PaymentJpaRepository paymentJpaRepository,
            PaymentMapper paymentMapper,
            QRCodeWriter qrCodeWriter,
            MercadoPagoIntegrationConfig mercadoPagoIntegrationConfig,
            MercadoPagoClient mercadoPagoClient,
            OrderRepositoryGateways orderRepositoryGateway,
            OrderMapper orderMapper,
            StreamBridge streamBridge
    ) {
        OrderCreatedHandler handler = new OrderCreatedHandler(
                paymentJpaRepository,
                paymentMapper,
                qrCodeWriter,
                mercadoPagoIntegrationConfig,
                mercadoPagoClient,
                orderRepositoryGateway,
                orderMapper,
                streamBridge
        );

        return handler::handle;
    }
}
