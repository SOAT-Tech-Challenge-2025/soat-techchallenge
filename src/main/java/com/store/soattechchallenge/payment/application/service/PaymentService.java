package com.store.soattechchallenge.payment.application.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.store.soattechchallenge.payment.application.usecases.PaymentUseCase;
import com.store.soattechchallenge.payment.domain.PaymentGateway;
import com.store.soattechchallenge.payment.domain.exception.PaymentAlreadyExists;
import com.store.soattechchallenge.payment.domain.exception.PaymentFinalizationException;
import com.store.soattechchallenge.payment.domain.exception.QRCodeGenerationException;
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
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.List;

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
        return this.paymentRepository.findById(id);
    }

    @Override
    public Payment create(String id, Double vlTotalPedido, List<Product> products) {
        if (this.paymentRepository.existsById(id)) {
            throw new PaymentAlreadyExists("Payment with ID " + id + "already exists");
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

        payment = this.paymentGateway.create(payment, products);
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
                throw new PaymentAlreadyExists("Payment with external ID " + idExterno + " already exists");
            }

            Payment payment = this.paymentRepository.findById(id);
            payment.setExternalId(idExterno);
            payment.finalize(this.paymentStatusMapper.toDomain(mpOrder.status()));
            return this.paymentRepository.save(payment);
        } catch (EntityNotFoundException error) {
            throw new PaymentFinalizationException("Payment not found");
        } catch (MPClientException error) {
            throw new PaymentFinalizationException("Error interacting with the payment gateway");
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
            throw new QRCodeGenerationException("Payment not found");
        } catch (WriterException e) {
            throw new QRCodeGenerationException("Error generation QR Code: " + e.getMessage());
        }
    }
}
