package com.store.soattechchallenge.payment.infrastructure.configuration;

import com.google.zxing.qrcode.QRCodeWriter;
import com.store.soattechchallenge.payment.application.gateways.EventPublisher;
import com.store.soattechchallenge.payment.application.gateways.PaymentGateway;
import com.store.soattechchallenge.payment.application.gateways.PaymentRepositoryGateway;
import com.store.soattechchallenge.payment.application.usecases.*;
import com.store.soattechchallenge.payment.controller.PaymentController;
import com.store.soattechchallenge.payment.domain.events.OrderCreatedEvent;
import com.store.soattechchallenge.payment.infrastructure.api.validator.MercadoPagoWebhookValidator;
import com.store.soattechchallenge.payment.infrastructure.gateways.MercadoPagoPaymentGateway;
import com.store.soattechchallenge.payment.infrastructure.gateways.StreamEventPublisher;
import com.store.soattechchallenge.payment.infrastructure.gateways.PaymentRepositoryJpaGateway;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.MercadoPagoClient;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.impl.MercadoPagoClientImpl;
import com.store.soattechchallenge.payment.infrastructure.jpa.PaymentJpaRepository;
import com.store.soattechchallenge.payment.infrastructure.mappers.PaymentMapper;
import com.store.soattechchallenge.payment.infrastructure.mappers.PaymentProductMapper;
import com.store.soattechchallenge.payment.infrastructure.mappers.PaymentStatusMapper;
import com.store.soattechchallenge.shoppingCart.order.application.usecases.FindOrdersUseCase;
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
    PaymentStatusMapper paymentStatusMapper () {
        return new PaymentStatusMapper();
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
    EventPublisher paymentEventsPublisher(StreamBridge streamBridge) {
        return new StreamEventPublisher(streamBridge);
    }

    @Bean
    PaymentRepositoryGateway paymentRepositoryGateway (
            PaymentJpaRepository paymentJpaRepository,
            PaymentMapper paymentMapper
    ) {
        return new PaymentRepositoryJpaGateway(paymentJpaRepository, paymentMapper);
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
    PaymentGateway paymentGateway (
            MercadoPagoIntegrationConfig mercadoPagoIntegrationConfig,
            MercadoPagoClient mercadoPagoClient
    ) {
        return new MercadoPagoPaymentGateway(mercadoPagoIntegrationConfig, mercadoPagoClient);
    }

    @Bean
    FindPaymentByIdUseCase findPaymentByIdUseCase (PaymentRepositoryGateway paymentRepositoryGateway) {
        return new FindPaymentByIdUseCase(paymentRepositoryGateway);
    }

    @Bean
    RenderQrCodeUseCase renderQrCodeUseCase (
            PaymentRepositoryGateway paymentRepositoryGateway,
            QRCodeWriter qrCodeWriter
    ) {
        return new RenderQrCodeUseCase(paymentRepositoryGateway, qrCodeWriter);
    }

    @Bean
    CreatePaymentUseCase createPaymentUseCase (
            PaymentRepositoryGateway paymentRepositoryGateway,
            PaymentGateway paymentGateway
    ) {
        return new CreatePaymentUseCase(paymentRepositoryGateway, paymentGateway);
    }

    @Bean
    FinalizePaymentByMercadoPagoPaymentIdUseCase finalizePaymentByMercadoPagoPaymentIdUseCase (
            PaymentRepositoryGateway paymentRepositoryGateway,
            MercadoPagoClient mercadoPagoClient,
            PaymentStatusMapper paymentStatusMapper,
            EventPublisher eventPublisher
    ) {
        return new FinalizePaymentByMercadoPagoPaymentIdUseCase(
                paymentRepositoryGateway,
                mercadoPagoClient,
                paymentStatusMapper,
                eventPublisher
        );
    }

    @Bean
    HandleOrderCreatedUseCase handleOrderCreatedUseCase(
            CreatePaymentUseCase createPaymentUseCase,
            FindOrdersUseCase findOrdersUseCase
    ) {
        return new HandleOrderCreatedUseCase(createPaymentUseCase, findOrdersUseCase);
    }

    @Bean
    PaymentController paymentController (
            FindPaymentByIdUseCase findPaymentByIdUseCase,
            RenderQrCodeUseCase renderQrCodeUseCase,
            CreatePaymentUseCase createPaymentUseCase,
            FinalizePaymentByMercadoPagoPaymentIdUseCase finalizePaymentByMercadoPagoPaymentIdUseCase,
            HandleOrderCreatedUseCase handleOrderCreatedUseCase
    ) {
        return new PaymentController(
                findPaymentByIdUseCase,
                renderQrCodeUseCase,
                createPaymentUseCase,
                finalizePaymentByMercadoPagoPaymentIdUseCase,
                handleOrderCreatedUseCase
        );
    }

    @Bean
    public Consumer<OrderCreatedEvent> consumeOrderCreatedEvent(PaymentController paymentController) {
        return paymentController::handleOrderCreatedEvent;
    }
}
