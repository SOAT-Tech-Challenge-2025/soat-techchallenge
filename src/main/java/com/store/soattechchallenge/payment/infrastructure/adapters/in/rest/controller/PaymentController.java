package com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.controller;

import com.store.soattechchallenge.payment.application.usecases.PaymentUseCase;
import com.store.soattechchallenge.payment.domain.model.Payment;
import com.store.soattechchallenge.payment.domain.model.Product;
import com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto.MercadoPagoWebhookDTO;
import com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto.PaymentCreateRequestDTO;
import com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto.PaymentResponseDTO;
import com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.validator.impl.MercadoPagoWebhookValidator;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers.PaymentMapper;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers.PaymentProductMapper;
import com.store.soattechchallenge.utils.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentUseCase paymentService;
    private final PaymentMapper paymentMapper;
    private final MercadoPagoWebhookValidator mercadoPagoWebhookValidator;
    private final PaymentProductMapper paymentProductMapper;

    public PaymentController(
            PaymentUseCase paymentService,
            PaymentMapper paymentMapper,
            MercadoPagoWebhookValidator mercadoPagoWebhookValidator,
            PaymentProductMapper paymentProductMapper
    ) {
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
        this.mercadoPagoWebhookValidator = mercadoPagoWebhookValidator;
        this.paymentProductMapper = paymentProductMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> find(@PathVariable String id) {
        Payment payment = this.paymentService.find(id);
        return ResponseEntity.ok(this.paymentMapper.toDTO(payment));
    }

    @GetMapping("/{id}/qr")
    public void getQrCode(@PathVariable String id, HttpServletResponse response) {
        try {
            BufferedImage qrImage = this.paymentService.renderQrCode(id, 600, 600);
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            ImageIO.write(qrImage, "PNG", response.getOutputStream());
        } catch (IOException error) {
            throw new CustomException(
                    error.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PaymentResponseDTO> create(@Valid @RequestBody PaymentCreateRequestDTO paymentRequestDTO) {
        List<Product> domainProducts = paymentRequestDTO
                .products()
                .stream()
                .map(this.paymentProductMapper::createDTOToDomain).collect(Collectors.toList());

        Payment payment = this.paymentService.create(
                paymentRequestDTO.id(),
                paymentRequestDTO.totalOrderValue(),
                domainProducts
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(this.paymentMapper.toDTO(payment));
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
        Payment payment = this.paymentService.finalizeByMercadoPagoPaymentId(
                mercadoPagoWebhookDTO.data().id()
        );

        return ResponseEntity.ok(this.paymentMapper.toDTO(payment));
    }
}
