package com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.in.rest.controller;

import com.store.soattechchallenge.preparacao.application.usecases.PreparacaoUseCase;
import com.store.soattechchallenge.preparacao.domain.exception.PreparacaoAlreadyExistsException;
import com.store.soattechchallenge.preparacao.domain.model.Preparacao;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.in.rest.dto.PreparacaoCreateRequestDTO;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.in.rest.dto.PreparacaoResponseDTO;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.out.mapper.PreparacaoMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/preparacao")
public class PreparacaoController {
    private final PreparacaoUseCase preparacaoUseCase;
    private final PreparacaoMapper preparacaoMapper;

    public PreparacaoController(PreparacaoUseCase pagamentoService, PreparacaoMapper preparacaoMapper) {
        this.preparacaoUseCase = pagamentoService;
        this.preparacaoMapper = preparacaoMapper;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PreparacaoResponseDTO> create(
            @RequestBody PreparacaoCreateRequestDTO preparacaoCreateRequestDTO
    ) {
        try {
            Preparacao preparacao = this.preparacaoUseCase.create(
                    preparacaoCreateRequestDTO.id(),
                    preparacaoCreateRequestDTO.tempoDePreparacao()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(this.preparacaoMapper.toDTO(preparacao));
        } catch (PreparacaoAlreadyExistsException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        }

    }
}
