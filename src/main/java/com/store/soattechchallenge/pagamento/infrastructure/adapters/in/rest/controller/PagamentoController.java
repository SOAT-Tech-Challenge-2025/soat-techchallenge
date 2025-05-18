package com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.controller;

import com.store.soattechchallenge.pagamento.application.usecases.PagamentoUseCase;
import com.store.soattechchallenge.pagamento.domain.exceptions.*;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.MercadoPagoWebhookDTO;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.PagamentoCreateRequestDTO;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.PagamentoResponseDTO;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers.PagamentoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {
    private final PagamentoUseCase pagamentoService;
    private final PagamentoMapper pagamentoMapper;

    public PagamentoController (
            PagamentoUseCase pagamentoService,
            PagamentoMapper pagamentoMapper
    ) {
        this.pagamentoService = pagamentoService;
        this.pagamentoMapper = pagamentoMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> find(@PathVariable String id) {
        try {
            Pagamento pagamento = this.pagamentoService.find(id);
            return ResponseEntity.ok(this.pagamentoMapper.toDTO(pagamento));
        } catch (NotFound error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage());
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
        } catch (AlreadyExists error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        } catch (CreatePagamentoError error) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage());
        }
    }

    @PostMapping("/notificacoes/mercado-pago")
    @Transactional
    public ResponseEntity<PagamentoResponseDTO> finalize(@RequestBody MercadoPagoWebhookDTO mercadoPagoWebhookDTO) {
        if (
                mercadoPagoWebhookDTO.action() == null ||
                mercadoPagoWebhookDTO.type() == null ||
                !mercadoPagoWebhookDTO.action().equals("payment.created") ||
                !mercadoPagoWebhookDTO.type().equals("payment")
        ) {
            return ResponseEntity.noContent().build();
        }

        try {
            Pagamento pagamento = this.pagamentoService.finalizeByMercadoPagoPaymentId(
                    mercadoPagoWebhookDTO.data().id()
            );

            return ResponseEntity.ok(this.pagamentoMapper.toDTO(pagamento));
        } catch (NotFound error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage());
        } catch (PagamentoAlreadyFinalized error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        } catch (FinalizePagamentoError error) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage());
        }
    }
}
