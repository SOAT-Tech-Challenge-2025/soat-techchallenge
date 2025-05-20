package com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.in.rest.controller;

import com.store.soattechchallenge.preparacao.application.usecases.PreparacaoUseCase;
import com.store.soattechchallenge.preparacao.domain.exception.InvalidPreparacaoException;
import com.store.soattechchallenge.preparacao.domain.exception.NoPreparacaoToStartException;
import com.store.soattechchallenge.preparacao.domain.exception.PreparacaoAlreadyExistsException;
import com.store.soattechchallenge.preparacao.domain.model.Preparacao;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.in.rest.dto.PreparacaoCreateRequestDTO;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.in.rest.dto.PreparacaoResponseDTO;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.out.mapper.PreparacaoMapper;
import com.store.soattechchallenge.preparacao.infrastructure.adapters.out.integrations.adapters.out.repository.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/iniciar-proximo")
    @Transactional
    public ResponseEntity<PreparacaoResponseDTO> startNext() {
        try {
            Preparacao preparacao = this.preparacaoUseCase.startNext();
            return ResponseEntity.status(HttpStatus.OK).body(this.preparacaoMapper.toDTO(preparacao));
        } catch (NoPreparacaoToStartException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        }
    }

    @PostMapping("/{id}/pronto")
    @Transactional
    public ResponseEntity<PreparacaoResponseDTO> ready(@PathVariable String id) {
        try {
            Preparacao preparacao = this.preparacaoUseCase.ready(id);
            return ResponseEntity.status(HttpStatus.OK).body(this.preparacaoMapper.toDTO(preparacao));
        } catch (EntityNotFoundException error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage());
        } catch (InvalidPreparacaoException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        }
    }

    @PostMapping("/{id}/finalize")
    @Transactional
    public ResponseEntity<PreparacaoResponseDTO> finalize(@PathVariable String id) {
        try {
            Preparacao preparacao = this.preparacaoUseCase.finalize(id);
            return ResponseEntity.status(HttpStatus.OK).body(this.preparacaoMapper.toDTO(preparacao));
        } catch (EntityNotFoundException error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage());
        } catch (InvalidPreparacaoException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        }
    }

    @GetMapping("/fila-de-espera")
    public ResponseEntity<List<PreparacaoResponseDTO>> waitingList() {
        List<Preparacao> preparacoes = this.preparacaoUseCase.waitingList();
        return ResponseEntity.ok(
                preparacoes
                        .stream()
                        .map(this.preparacaoMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }
}
