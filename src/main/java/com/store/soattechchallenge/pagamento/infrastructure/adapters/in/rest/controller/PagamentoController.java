package com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.controller;

import com.store.soattechchallenge.pagamento.application.service.PagamentoService;
import com.store.soattechchallenge.pagamento.domain.exceptions.NotFound;
import com.store.soattechchallenge.pagamento.domain.exceptions.PagamentoAlreadyFinalized;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.PagamentoCreateRequestDTO;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.PagamentoFinalizeRequestDTO;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.PagamentoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {
    @Autowired
    PagamentoService pagamentoService;

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> find(@PathVariable String id) {
        try {
            Pagamento pagamento = this.pagamentoService.find(id);
            return ResponseEntity.ok(
                    new PagamentoResponseDTO(
                            pagamento.getId(),
                            pagamento.getIdExterno(),
                            pagamento.getStPagamento(),
                            pagamento.getVlTotalPedido(),
                            pagamento.getCodigoQr(),
                            pagamento.getExpiracao(),
                            pagamento.getDtInclusao(),
                            pagamento.getTimestamp()
                    )
            );
        } catch (NotFound error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage());
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PagamentoResponseDTO> create(@RequestBody PagamentoCreateRequestDTO pagamentoRequestDTO) {
        Pagamento pagamento = this.pagamentoService.create(
                pagamentoRequestDTO.id(),
                pagamentoRequestDTO.vlTotalPedido(),
                pagamentoRequestDTO.produtos()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new PagamentoResponseDTO(
                        pagamento.getId(),
                        pagamento.getIdExterno(),
                        pagamento.getStPagamento(),
                        pagamento.getVlTotalPedido(),
                        pagamento.getCodigoQr(),
                        pagamento.getExpiracao(),
                        pagamento.getDtInclusao(),
                        pagamento.getTimestamp()
                )
        );
    }

    @PostMapping("/{id}/finalize")
    @Transactional
    public ResponseEntity<PagamentoResponseDTO> finalize(
            @PathVariable String id,
            @RequestBody PagamentoFinalizeRequestDTO pagamentoFinalizeRequestDTO
    ) {
        try {
            Pagamento pagamento = this.pagamentoService.finalize(id, pagamentoFinalizeRequestDTO.stPagamento());
            return ResponseEntity.ok(
                    new PagamentoResponseDTO(
                            pagamento.getId(),
                            pagamento.getIdExterno(),
                            pagamento.getStPagamento(),
                            pagamento.getVlTotalPedido(),
                            pagamento.getCodigoQr(),
                            pagamento.getExpiracao(),
                            pagamento.getDtInclusao(),
                            pagamento.getTimestamp()
                    )
            );
        } catch (NotFound error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage());
        } catch (PagamentoAlreadyFinalized error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        }
    }
}
