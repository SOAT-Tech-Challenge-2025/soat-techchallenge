package com.store.soattechchallenge.preparation.infrastructure.api.controller;

import com.store.soattechchallenge.preparation.application.usecases.commands.CreatePreparationCommand;
import com.store.soattechchallenge.preparation.application.usecases.commands.MarkPreparationAsCompletedCommand;
import com.store.soattechchallenge.preparation.application.usecases.commands.MarkPreparationAsReadyCommand;
import com.store.soattechchallenge.preparation.controller.PreparationController;
import com.store.soattechchallenge.preparation.domain.entites.Preparation;
import com.store.soattechchallenge.preparation.infrastructure.api.dto.PreparationCreateRequestDTO;
import com.store.soattechchallenge.preparation.infrastructure.api.dto.PreparationResponseDTO;
import com.store.soattechchallenge.preparation.infrastructure.mappers.PreparationMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/preparation")
public class PreparationAPIController {
    private final PreparationController preparationController;
    private final PreparationMapper preparationMapper;

    public PreparationAPIController(
            PreparationController preparationController,
            PreparationMapper preparationMapper
    ) {
        this.preparationController = preparationController;
        this.preparationMapper = preparationMapper;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PreparationResponseDTO> create(
            @Valid @RequestBody PreparationCreateRequestDTO preparationCreateRequestDTO
    ) {
        CreatePreparationCommand command = new CreatePreparationCommand(
                preparationCreateRequestDTO.id(),
                preparationCreateRequestDTO.preparationTime()
        );

        Preparation preparation = this.preparationController.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.preparationMapper.fromDomainToDTO(preparation));
    }

    @PostMapping("/start-next")
    @Transactional
    public ResponseEntity<PreparationResponseDTO> startNext() {
        Preparation preparation = this.preparationController.startNext();
        return ResponseEntity.status(HttpStatus.OK).body(this.preparationMapper.fromDomainToDTO(preparation));
    }

    @PostMapping("/{id}/ready")
    @Transactional
    public ResponseEntity<PreparationResponseDTO> ready(@PathVariable String id) {
        MarkPreparationAsReadyCommand command = new MarkPreparationAsReadyCommand(id);
        Preparation preparation = this.preparationController.markAsReady(command);
        return ResponseEntity.status(HttpStatus.OK).body(this.preparationMapper.fromDomainToDTO(preparation));
    }

    @PostMapping("/{id}/complete")
    @Transactional
    public ResponseEntity<PreparationResponseDTO> complete(@PathVariable String id) {
        MarkPreparationAsCompletedCommand command = new MarkPreparationAsCompletedCommand(id);
        Preparation preparation = this.preparationController.markAsCompleted(command);
        return ResponseEntity.status(HttpStatus.OK).body(this.preparationMapper.fromDomainToDTO(preparation));
    }

    @GetMapping("/waiting-list")
    public ResponseEntity<List<PreparationResponseDTO>> waitingList() {
        List<Preparation> preparations = this.preparationController.getWaitingList();
        return ResponseEntity.ok(
                preparations
                        .stream()
                        .map(this.preparationMapper::fromDomainToDTO)
                        .collect(Collectors.toList())
        );
    }
}
