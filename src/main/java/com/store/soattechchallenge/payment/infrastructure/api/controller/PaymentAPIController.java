package com.store.soattechchallenge.payment.infrastructure.api.controller;

import com.store.soattechchallenge.payment.application.usecases.commands.FinalizePaymentByMercadoPagoPaymentIdCommand;
import com.store.soattechchallenge.payment.domain.entities.Payment;
import com.store.soattechchallenge.payment.application.usecases.commands.CreatePaymentCommand;
import com.store.soattechchallenge.payment.application.usecases.commands.FindPaymentByIdCommand;
import com.store.soattechchallenge.payment.application.usecases.commands.RenderQrCodeCommand;
import com.store.soattechchallenge.payment.controller.PaymentController;
import com.store.soattechchallenge.payment.infrastructure.api.dto.PaymentResponseDTO;
import com.store.soattechchallenge.payment.infrastructure.api.dto.MercadoPagoWebhookDTO;
import com.store.soattechchallenge.payment.infrastructure.api.dto.PaymentCreateRequestDTO;
import com.store.soattechchallenge.payment.infrastructure.api.validator.MercadoPagoWebhookValidator;
import com.store.soattechchallenge.payment.infrastructure.mappers.PaymentMapper;
import com.store.soattechchallenge.payment.infrastructure.mappers.PaymentProductMapper;
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
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment")
public class PaymentAPIController {
    private final PaymentController paymentController;
    private final PaymentMapper paymentMapper;
    private final MercadoPagoWebhookValidator mercadoPagoWebhookValidator;
    private final PaymentProductMapper paymentProductMapper;

    public PaymentAPIController(
            PaymentController paymentController,
            PaymentMapper paymentMapper,
            MercadoPagoWebhookValidator mercadoPagoWebhookValidator,
            PaymentProductMapper paymentProductMapper
    ) {
        this.paymentController = paymentController;
        this.paymentMapper = paymentMapper;
        this.mercadoPagoWebhookValidator = mercadoPagoWebhookValidator;
        this.paymentProductMapper = paymentProductMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> find(@PathVariable String id) {
        FindPaymentByIdCommand command = new FindPaymentByIdCommand(id);
        Payment payment = this.paymentController.findById(command);
        return ResponseEntity.ok(this.paymentMapper.fromDomainToDTO(payment));
    }

    @GetMapping("/{id}/qr")
    public void getQrCode(@PathVariable String id, HttpServletResponse response) {
        try {
            RenderQrCodeCommand command = new RenderQrCodeCommand(id, 600, 600);
            BufferedImage qrImage = this.paymentController.renderQrCode(command);
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
        CreatePaymentCommand command = new CreatePaymentCommand(
                paymentRequestDTO.id(),
                paymentRequestDTO.totalOrderValue(),
                paymentRequestDTO.products()
                        .stream()
                        .map(this.paymentProductMapper::apiCreateDTOToGatewayDTO)
                        .collect(Collectors.toList())
        );

        Payment payment = this.paymentController.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.paymentMapper.fromDomainToDTO(payment));
    }

    @PostMapping("/notifications/mercado-pago")
    @Transactional
    public ResponseEntity<PaymentResponseDTO> mercadoPagoWebhook(
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
        FinalizePaymentByMercadoPagoPaymentIdCommand command = new FinalizePaymentByMercadoPagoPaymentIdCommand(
                mercadoPagoWebhookDTO.data().id()
        );

        Payment payment = this.paymentController.finalizePaymentByMercadoPago(
                command
        );

        return ResponseEntity.ok(this.paymentMapper.fromDomainToDTO(payment));
    }
}
