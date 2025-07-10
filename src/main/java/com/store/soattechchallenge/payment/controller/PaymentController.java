package com.store.soattechchallenge.payment.controller;

import com.store.soattechchallenge.payment.application.usecases.CreatePaymentUseCase;
import com.store.soattechchallenge.payment.application.usecases.FinalizePaymentByMercadoPagoPaymentIdUseCase;
import com.store.soattechchallenge.payment.application.usecases.FindPaymentByIdUseCase;
import com.store.soattechchallenge.payment.application.usecases.RenderQrCodeUseCase;
import com.store.soattechchallenge.payment.application.usecases.commands.CreatePaymentCommand;
import com.store.soattechchallenge.payment.application.usecases.commands.FinalizePaymentByMercadoPagoPaymentIdCommand;
import com.store.soattechchallenge.payment.application.usecases.commands.FindPaymentByIdCommand;
import com.store.soattechchallenge.payment.application.usecases.commands.RenderQrCodeCommand;
import com.store.soattechchallenge.payment.domain.entities.Payment;

import java.awt.image.BufferedImage;

public class PaymentController {
    private final FindPaymentByIdUseCase findPaymentByIdUseCase;
    private final RenderQrCodeUseCase renderQrCodeUseCase;
    private final CreatePaymentUseCase createPaymentUseCase;
    private final FinalizePaymentByMercadoPagoPaymentIdUseCase finalizePaymentByMercadoPagoPaymentIdUseCase;

    public PaymentController(
            FindPaymentByIdUseCase findPaymentByIdUseCase,
            RenderQrCodeUseCase renderQrCodeUseCase,
            CreatePaymentUseCase createPaymentUseCase,
            FinalizePaymentByMercadoPagoPaymentIdUseCase finalizePaymentByMercadoPagoPaymentIdUseCase
    ) {
        this.findPaymentByIdUseCase = findPaymentByIdUseCase;
        this.renderQrCodeUseCase = renderQrCodeUseCase;
        this.createPaymentUseCase = createPaymentUseCase;
        this.finalizePaymentByMercadoPagoPaymentIdUseCase = finalizePaymentByMercadoPagoPaymentIdUseCase;
    }

    public Payment findById(FindPaymentByIdCommand command) {
        return this.findPaymentByIdUseCase.execute(command);
    }

    public BufferedImage renderQrCode(RenderQrCodeCommand command) {
        return this.renderQrCodeUseCase.execute(command);
    }

    public Payment create(CreatePaymentCommand command) {
        return this.createPaymentUseCase.execute(command);
    }

    public Payment finalizePaymentByMercadoPago(FinalizePaymentByMercadoPagoPaymentIdCommand command) {
        return this.finalizePaymentByMercadoPagoPaymentIdUseCase.execute(command);
    }
}
