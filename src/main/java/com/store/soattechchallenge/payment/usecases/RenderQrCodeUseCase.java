package com.store.soattechchallenge.payment.usecases;

import com.store.soattechchallenge.payment.gateways.QrCodeWriterGateway;
import org.springframework.http.HttpStatus;
import com.store.soattechchallenge.payment.domain.entities.Payment;
import com.store.soattechchallenge.payment.gateways.exceptions.EntityNotFoundException;
import com.store.soattechchallenge.payment.gateways.PaymentRepositoryGateway;
import com.store.soattechchallenge.payment.usecases.commands.RenderQrCodeCommand;
import com.store.soattechchallenge.utils.exception.CustomException;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.UUID;

public class RenderQrCodeUseCase {
    private final PaymentRepositoryGateway paymentRepositoryGateway;
    private final QrCodeWriterGateway qrCodeWriterGateway;

    public RenderQrCodeUseCase(
            PaymentRepositoryGateway paymentRepositoryGateway,
            QrCodeWriterGateway qrCodeWriterGateway
    ) {
        this.paymentRepositoryGateway = paymentRepositoryGateway;
        this.qrCodeWriterGateway = qrCodeWriterGateway;
    }

    public BufferedImage execute(RenderQrCodeCommand command) {
        try {
            Payment payment = this.paymentRepositoryGateway.findById(command.id());
            return this.qrCodeWriterGateway.render(payment.getQrCode(), command.width(), command.height());
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
}
