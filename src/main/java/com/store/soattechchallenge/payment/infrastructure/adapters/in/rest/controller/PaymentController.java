package com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.controller;

import com.store.soattechchallenge.payment.application.usecases.PaymentUseCase;
import com.store.soattechchallenge.payment.domain.exception.*;
import com.store.soattechchallenge.payment.domain.model.Payment;
import com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto.MercadoPagoWebhookDTO;
import com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto.PaymentCreateRequestDTO;
import com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto.PaymentResponseDTO;
import com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.validator.impl.MercadoPagoWebhookValidator;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers.PaymentMapper;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.repository.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentUseCase paymentService;
    private final PaymentMapper paymentMapper;
    private final MercadoPagoWebhookValidator mercadoPagoWebhookValidator;

    public PaymentController(
            PaymentUseCase paymentService,
            PaymentMapper paymentMapper,
            MercadoPagoWebhookValidator mercadoPagoWebhookValidator
    ) {
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
        this.mercadoPagoWebhookValidator = mercadoPagoWebhookValidator;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> find(@PathVariable String id) {
        try {
            Payment payment = this.paymentService.find(id);
            return ResponseEntity.ok(this.paymentMapper.toDTO(payment));
        } catch (EntityNotFoundException error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage());
        }
    }

    @GetMapping("/{id}/qr")
    public void getCodigoQr(@PathVariable String id, HttpServletResponse response) {
        try {
            BufferedImage qrImage = this.paymentService.renderCodigoQr(id, 600, 600);
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            ImageIO.write(qrImage, "PNG", response.getOutputStream());
        } catch (IOException | QRCodeGenerationException error) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage());
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PaymentResponseDTO> create(@RequestBody PaymentCreateRequestDTO paymentRequestDTO) {
        try {
            Payment payment = this.paymentService.create(
                    paymentRequestDTO.id(),
                    paymentRequestDTO.totalOrderValue(),
                    paymentRequestDTO.products()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(this.paymentMapper.toDTO(payment));
        } catch (PaymentAlreadyExists error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        } catch (PaymentCreationException error) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage());
        }
    }

    @PostMapping("/notifications/mercado-pago")
    @Transactional
    public ResponseEntity<PaymentResponseDTO> finalize(
            HttpServletRequest request,
            @RequestBody MercadoPagoWebhookDTO mercadoPagoWebhookDTO
    ) {
        if (
                mercadoPagoWebhookDTO.action() == null ||
                mercadoPagoWebhookDTO.type() == null ||
                !mercadoPagoWebhookDTO.action().equals("payment.created") ||
                !mercadoPagoWebhookDTO.type().equals("payment")
        ) {
            return ResponseEntity.noContent().build();
        }

        this.mercadoPagoWebhookValidator.validate(request);
        try {
            Payment payment = this.paymentService.finalizeByMercadoPagoPaymentId(
                    mercadoPagoWebhookDTO.data().id()
            );

            return ResponseEntity.ok(this.paymentMapper.toDTO(payment));
        } catch (EntityNotFoundException error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage());
        } catch (PaymentAlreadyFinalizedException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        } catch (PaymentFinalizationException error) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage());
        }
    }
}
