package com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.controller;

import com.store.soattechchallenge.pagamento.application.usecases.PagamentoUseCase;
import com.store.soattechchallenge.pagamento.domain.exception.*;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.MercadoPagoWebhookDTO;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.PagamentoCreateRequestDTO;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.PagamentoResponseDTO;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.validator.impl.MercadoPagoWebhookValidator;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers.PagamentoMapper;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.repository.exception.EntityNotFoundException;
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
@RequestMapping("/pagamento")
public class PagamentoController {
    private final PagamentoUseCase pagamentoService;
    private final PagamentoMapper pagamentoMapper;
    private final MercadoPagoWebhookValidator mercadoPagoWebhookValidator;

    public PagamentoController (
            PagamentoUseCase pagamentoService,
            PagamentoMapper pagamentoMapper,
            MercadoPagoWebhookValidator mercadoPagoWebhookValidator
    ) {
        this.pagamentoService = pagamentoService;
        this.pagamentoMapper = pagamentoMapper;
        this.mercadoPagoWebhookValidator = mercadoPagoWebhookValidator;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> find(@PathVariable String id) {
        try {
            Pagamento pagamento = this.pagamentoService.find(id);
            return ResponseEntity.ok(this.pagamentoMapper.toDTO(pagamento));
        } catch (EntityNotFoundException error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage());
        }
    }

    @GetMapping("/{id}/qr")
    public void getCodigoQr(@PathVariable String id, HttpServletResponse response) {
        try {
            BufferedImage qrImage = this.pagamentoService.renderCodigoQr(id, 600, 600);
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            ImageIO.write(qrImage, "PNG", response.getOutputStream());
        } catch (IOException | CodigoQRGenerationException error) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage());
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PagamentoResponseDTO> create(@RequestBody PagamentoCreateRequestDTO pagamentoRequestDTO) {
        try {
            Pagamento pagamento = this.pagamentoService.create(
                    pagamentoRequestDTO.id(),
                    pagamentoRequestDTO.vlTotalPedido(),
                    pagamentoRequestDTO.produtos()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(this.pagamentoMapper.toDTO(pagamento));
        } catch (PagamentoAlreadyExists error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        } catch (PagamentoCreationException error) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage());
        }
    }

    @PostMapping("/notificacoes/mercado-pago")
    @Transactional
    public ResponseEntity<PagamentoResponseDTO> finalize(
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
            Pagamento pagamento = this.pagamentoService.finalizeByMercadoPagoPaymentId(
                    mercadoPagoWebhookDTO.data().id()
            );

            return ResponseEntity.ok(this.pagamentoMapper.toDTO(pagamento));
        } catch (EntityNotFoundException error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage());
        } catch (PagamentoAlreadyFinalizedException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        } catch (PagamentoFinalizationException error) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage());
        }
    }
}
