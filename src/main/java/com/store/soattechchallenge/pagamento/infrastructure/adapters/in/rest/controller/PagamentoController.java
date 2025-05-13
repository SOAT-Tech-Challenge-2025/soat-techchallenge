package com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.controller;

import com.store.soattechchallenge.pagamento.application.service.PagamentoService;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.PagamentoCreateRequestDTO;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.PagamentoFinishRequestDTO;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.in.rest.dto.PagamentoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {
    @Autowired
    PagamentoService pagamentoService;

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> find(@PathVariable String id) {
        Pagamento pagamento = this.pagamentoService.find(id);
        return ResponseEntity.ok(
                new PagamentoResponseDTO(
                        pagamento.id(),
                        pagamento.idExterno(),
                        pagamento.stPagamento(),
                        pagamento.vlTotalPedido(),
                        pagamento.codigoQr(),
                        pagamento.expiracao(),
                        pagamento.dtInclusao(),
                        pagamento.timestamp()
                )
        );
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
                        pagamento.id(),
                        pagamento.idExterno(),
                        pagamento.stPagamento(),
                        pagamento.vlTotalPedido(),
                        pagamento.codigoQr(),
                        pagamento.expiracao(),
                        pagamento.dtInclusao(),
                        pagamento.timestamp()
                )
        );
    }

    @PostMapping("/{id}/finish")
    @Transactional
    public ResponseEntity<PagamentoResponseDTO> finish(
            @PathVariable String id,
            @RequestBody PagamentoFinishRequestDTO pagamentoFinishRequestDTO
    ) {
        Pagamento pagamento = this.pagamentoService.finish(id, pagamentoFinishRequestDTO.stPagamento());
        return ResponseEntity.ok(
                new PagamentoResponseDTO(
                        pagamento.id(),
                        pagamento.idExterno(),
                        pagamento.stPagamento(),
                        pagamento.vlTotalPedido(),
                        pagamento.codigoQr(),
                        pagamento.expiracao(),
                        pagamento.dtInclusao(),
                        pagamento.timestamp()
                )
        );
    }
}
