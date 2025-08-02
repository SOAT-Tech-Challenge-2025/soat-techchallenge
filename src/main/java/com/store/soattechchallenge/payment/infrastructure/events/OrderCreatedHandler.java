package com.store.soattechchallenge.payment.infrastructure.events;

import com.google.zxing.qrcode.QRCodeWriter;
import com.store.soattechchallenge.payment.controller.PaymentController;
import com.store.soattechchallenge.payment.gateways.PaymentProductDTO;
import com.store.soattechchallenge.payment.infrastructure.configuration.MercadoPagoIntegrationConfig;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.MercadoPagoClient;
import com.store.soattechchallenge.payment.infrastructure.jpa.PaymentJpaRepository;
import com.store.soattechchallenge.payment.infrastructure.mappers.PaymentMapper;
import com.store.soattechchallenge.payment.usecases.commands.CreatePaymentCommand;
import com.store.soattechchallenge.shoppingCart.order.controller.OrderMainController;
import com.store.soattechchallenge.shoppingCart.order.gateways.OrderRepositoryGateways;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.OrderMapper;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.Optional;
import java.util.stream.Collectors;

public class OrderCreatedHandler {
    private final PaymentController paymentController;
    private final OrderMainController orderController;

    public OrderCreatedHandler(
            PaymentJpaRepository paymentJpaRepository,
            PaymentMapper paymentMapper,
            QRCodeWriter qrCodeWriter,
            MercadoPagoIntegrationConfig mercadoPagoIntegrationConfig,
            MercadoPagoClient mercadoPagoClient,
            OrderRepositoryGateways orderRepositoryGateway,
            OrderMapper orderMapper,
            StreamBridge streamBridge
    ) {
        this.paymentController = new PaymentController(
                paymentJpaRepository,
                paymentMapper,
                qrCodeWriter,
                mercadoPagoIntegrationConfig,
                mercadoPagoClient,
                streamBridge
        );

        this.orderController = new OrderMainController(
                orderRepositoryGateway,
                orderMapper,
                streamBridge
        );
    }

    public void handle(OrderCreatedEvent orderCreatedEvent) {
        Optional<OrderResponseDTO> orderResponseDTOOptional = this.orderController.getOrdeById(
                orderCreatedEvent.getOrderId()
        );

        if (orderResponseDTOOptional.isEmpty()) {
            throw new RuntimeException("Order " + orderCreatedEvent.getOrderId() + " not found");
        }

        OrderResponseDTO orderResponseDTO = orderResponseDTOOptional.get();
        CreatePaymentCommand command = new CreatePaymentCommand(
                orderResponseDTO.getOrderId(),
                orderResponseDTO.getTotalOrder(),
                orderResponseDTO
                        .getProducts()
                        .stream()
                        .map(productResponse -> new PaymentProductDTO(
                                productResponse.getProductId().toString(),
                                productResponse.getProductId().toString(),
                                productResponse.getVlUnitProduct(),
                                1
                        )).collect(Collectors.toList())

        );

        this.paymentController.create(command);
    }
}
