package com.store.soattechchallenge.payment.application.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.store.soattechchallenge.payment.application.usecases.PaymentUseCase;
import com.store.soattechchallenge.payment.domain.PaymentGateway;
import com.store.soattechchallenge.payment.domain.exception.*;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.repository.exception.EntityNotFoundException;
import com.store.soattechchallenge.payment.domain.model.Payment;
import com.store.soattechchallenge.payment.domain.model.Product;
import com.store.soattechchallenge.payment.domain.model.PaymentStatus;
import com.store.soattechchallenge.payment.domain.repository.PaymentRepository;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.MercadoPagoClient;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.exception.MPClientException;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.model.MPOrder;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.model.MPPayment;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers.PaymentStatusMapper;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService implements PaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;
    private final MercadoPagoClient mercadoPagoClient;
    private final PaymentStatusMapper paymentStatusMapper;

    public PaymentService(
            MercadoPagoClient mercadoPagoClient,
            PaymentRepository paymentRepository,
            PaymentGateway paymentGateway,
            PaymentStatusMapper paymentStatusMapper
    ) {
        this.paymentRepository = paymentRepository;
        this.mercadoPagoClient = mercadoPagoClient;
        this.paymentGateway = paymentGateway;
        this.paymentStatusMapper = paymentStatusMapper;
    }

    @Override
    public Payment find(String id) {
        try {
            return this.paymentRepository.findById(id);
        } catch (EntityNotFoundException error) {
            throw new CustomException(
                    "Payment not found",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Override
    public Payment create(String id, Double vlTotalPedido, List<Product> products) {
        if (this.paymentRepository.existsById(id)) {
            throw new CustomException(
                    "Payment with ID " + id + " already exists",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }

        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(10);
        LocalDateTime now = LocalDateTime.now();
        Payment payment = new Payment(
                id,
                "empty-" + id,
                PaymentStatus.OPENED,
                vlTotalPedido,
                "",
                expiracao,
                now,
                now
        );

        try {
            payment = this.paymentGateway.create(payment, products);
        } catch (PaymentCreationException error) {
            throw new CustomException(
                    error.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return this.paymentRepository.save(payment);
    }

    @Override
    public Payment finalizeByMercadoPagoPaymentId(String paymentId) {
        try {
            MPPayment mpPayment = this.mercadoPagoClient.findPaymentById(paymentId);
            MPOrder mpOrder = this.mercadoPagoClient.findOrderById(mpPayment.order().id());
            String id = mpOrder.externalReference();
            String idExterno = Long.toString(mpOrder.id());
            if (paymentRepository.existsByExternalId(idExterno)) {
                throw new CustomException(
                        "Payment with external ID " + idExterno + " already exists",
                        HttpStatus.BAD_REQUEST,
                        String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        LocalDateTime.now(),
                        UUID.randomUUID()
                );
            }

            Payment payment = this.paymentRepository.findById(id);
            payment.setExternalId(idExterno);
            payment.finalize(this.paymentStatusMapper.toDomain(mpOrder.status()));
            return this.paymentRepository.save(payment);
        } catch (EntityNotFoundException error) {
            throw new CustomException(
                    "Payment not found",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        } catch (PaymentAlreadyFinalizedException error) {
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

    @Override
    public BufferedImage renderCodigoQr(String id, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Payment payment = this.paymentRepository.findById(id);
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    payment.getQrCode(),
                    BarcodeFormat.QR_CODE,
                    width,
                    height
            );
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (EntityNotFoundException error) {
            throw new CustomException(
                    "Payment not found",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        } catch (WriterException e) {
            throw new CustomException(
                    "Error generation QR Code: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }
}
