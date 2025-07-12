package com.store.soattechchallenge.payment.application.usecases;

import com.store.soattechchallenge.payment.application.gateways.EventPublisher;
import com.store.soattechchallenge.payment.domain.entities.Payment;
import com.store.soattechchallenge.payment.application.gateways.PaymentRepositoryGateway;
import com.store.soattechchallenge.payment.application.usecases.commands.FinalizePaymentByMercadoPagoPaymentIdCommand;
import com.store.soattechchallenge.payment.application.gateways.exceptions.EntityNotFoundException;
import com.store.soattechchallenge.payment.domain.events.PaymentClosedEvent;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.MercadoPagoClient;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.exception.MPClientException;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.model.MPOrder;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.model.MPPayment;
import com.store.soattechchallenge.payment.infrastructure.mappers.PaymentStatusMapper;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class FinalizePaymentByMercadoPagoPaymentIdUseCase {
    private final PaymentRepositoryGateway paymentRepositoryGateway;
    private final MercadoPagoClient mercadoPagoClient;
    private final PaymentStatusMapper paymentStatusMapper;
    private final EventPublisher eventPublisher;

    public FinalizePaymentByMercadoPagoPaymentIdUseCase(
            PaymentRepositoryGateway paymentRepositoryGateway,
            MercadoPagoClient mercadoPagoClient,
            PaymentStatusMapper paymentStatusMapper,
            EventPublisher eventPublisher
    ) {
        this.paymentRepositoryGateway = paymentRepositoryGateway;
        this.mercadoPagoClient = mercadoPagoClient;
        this.paymentStatusMapper = paymentStatusMapper;
        this.eventPublisher = eventPublisher;
    }

    public Payment execute(FinalizePaymentByMercadoPagoPaymentIdCommand command) {
        try {
            MPPayment mpPayment = this.mercadoPagoClient.findPaymentById(command.paymentId());
            MPOrder mpOrder = this.mercadoPagoClient.findOrderById(mpPayment.order().id());
            String id = mpOrder.externalReference();
            String externalId = Long.toString(mpOrder.id());
            if (paymentRepositoryGateway.existsByExternalId(externalId)) {
                throw new CustomException(
                        "Payment with external ID " + externalId + " already exists",
                        HttpStatus.BAD_REQUEST,
                        String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        LocalDateTime.now(),
                        UUID.randomUUID()
                );
            }

            Payment payment = this.paymentRepositoryGateway.findById(id);
            payment.setExternalId(externalId);
            payment.finalize(this.paymentStatusMapper.fromMPToDomain(mpOrder.status()));
            payment = this.paymentRepositoryGateway.save(payment);
            PaymentClosedEvent paymentClosedEvent = new PaymentClosedEvent(payment.getId());
            this.eventPublisher.publish(paymentClosedEvent);
            return payment;
        } catch (EntityNotFoundException error) {
            throw new CustomException(
                    "Payment not found",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        } catch (IllegalArgumentException error) {
            throw new CustomException(
                    error.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        } catch (MPClientException error) {
            throw new CustomException(
                    "Error interacting with the payment gateway: " + error.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }
}
