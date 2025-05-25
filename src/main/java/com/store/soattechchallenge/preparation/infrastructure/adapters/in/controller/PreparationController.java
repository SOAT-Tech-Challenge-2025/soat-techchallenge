package com.store.soattechchallenge.preparation.infrastructure.adapters.in.controller;

import com.store.soattechchallenge.preparation.application.usecases.PreparationUseCase;
import com.store.soattechchallenge.preparation.domain.exception.InvalidPreparationException;
import com.store.soattechchallenge.preparation.domain.exception.NoPreparationToStartException;
import com.store.soattechchallenge.preparation.domain.exception.PreparationAlreadyExistsException;
import com.store.soattechchallenge.preparation.domain.model.Preparation;
import com.store.soattechchallenge.preparation.infrastructure.adapters.in.dto.PreparationCreateRequestDTO;
import com.store.soattechchallenge.preparation.infrastructure.adapters.in.dto.PreparationResponseDTO;
import com.store.soattechchallenge.preparation.infrastructure.adapters.out.mapper.PreparationMapper;
import com.store.soattechchallenge.preparation.infrastructure.adapters.out.repository.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/preparation")
public class PreparationController {
    private final PreparationUseCase preparationUseCase;
    private final PreparationMapper preparationMapper;

    public PreparationController(PreparationUseCase preparationService, PreparationMapper preparationMapper) {
        this.preparationUseCase = preparationService;
        this.preparationMapper = preparationMapper;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PreparationResponseDTO> create(
            @Valid @RequestBody PreparationCreateRequestDTO preparationCreateRequestDTO
    ) {
        try {
            Preparation preparation = this.preparationUseCase.create(
                    preparationCreateRequestDTO.id(),
                    preparationCreateRequestDTO.preparationTime()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(this.preparationMapper.toDTO(preparation));
        } catch (PreparationAlreadyExistsException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        }
    }

    @PostMapping("/start-next")
    @Transactional
    public ResponseEntity<PreparationResponseDTO> startNext() {
        try {
            Preparation preparation = this.preparationUseCase.startNext();
            return ResponseEntity.status(HttpStatus.OK).body(this.preparationMapper.toDTO(preparation));
        } catch (NoPreparationToStartException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        }
    }

    @PostMapping("/{id}/ready")
    @Transactional
    public ResponseEntity<PreparationResponseDTO> ready(@PathVariable String id) {
        try {
            Preparation preparation = this.preparationUseCase.ready(id);
            return ResponseEntity.status(HttpStatus.OK).body(this.preparationMapper.toDTO(preparation));
        } catch (EntityNotFoundException error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage());
        } catch (InvalidPreparationException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        }
    }

    @PostMapping("/{id}/finalize")
    @Transactional
    public ResponseEntity<PreparationResponseDTO> finalize(@PathVariable String id) {
        try {
            Preparation preparation = this.preparationUseCase.finalize(id);
            return ResponseEntity.status(HttpStatus.OK).body(this.preparationMapper.toDTO(preparation));
        } catch (EntityNotFoundException error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage());
        } catch (InvalidPreparationException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage());
        }
    }

    @GetMapping("/waiting-list")
    public ResponseEntity<List<PreparationResponseDTO>> waitingList() {
        List<Preparation> preparations = this.preparationUseCase.waitingList();
        return ResponseEntity.ok(
                preparations
                        .stream()
                        .map(this.preparationMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }
}
