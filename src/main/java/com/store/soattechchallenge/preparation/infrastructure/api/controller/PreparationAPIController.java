package com.store.soattechchallenge.preparation.infrastructure.api.controller;

import com.store.soattechchallenge.preparation.infrastructure.api.dto.ItemsResponseDTO;
import com.store.soattechchallenge.preparation.infrastructure.jpa.PreparationJpaRepository;
import com.store.soattechchallenge.preparation.usecases.commands.CreatePreparationCommand;
import com.store.soattechchallenge.preparation.usecases.commands.MarkPreparationAsCompletedCommand;
import com.store.soattechchallenge.preparation.usecases.commands.MarkPreparationAsReadyCommand;
import com.store.soattechchallenge.preparation.controller.PreparationController;
import com.store.soattechchallenge.preparation.infrastructure.api.dto.PreparationCreateRequestDTO;
import com.store.soattechchallenge.preparation.infrastructure.api.dto.PreparationResponseDTO;
import com.store.soattechchallenge.preparation.infrastructure.mappers.PreparationMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preparation")
public class PreparationAPIController {
    private final PreparationController preparationController;

    public PreparationAPIController(
            PreparationJpaRepository preparationJpaRepository,
            PreparationMapper preparationMapper
    ) {
        this.preparationController = new PreparationController(preparationJpaRepository, preparationMapper);
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

        PreparationResponseDTO dto = this.preparationController.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/start-next")
    @Transactional
    public ResponseEntity<PreparationResponseDTO> startNext() {
        PreparationResponseDTO dto = this.preparationController.startNext();
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/{id}/ready")
    @Transactional
    public ResponseEntity<PreparationResponseDTO> ready(@PathVariable String id) {
        MarkPreparationAsReadyCommand command = new MarkPreparationAsReadyCommand(id);
        PreparationResponseDTO dto = this.preparationController.markAsReady(command);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/{id}/complete")
    @Transactional
    public ResponseEntity<PreparationResponseDTO> complete(@PathVariable String id) {
        MarkPreparationAsCompletedCommand command = new MarkPreparationAsCompletedCommand(id);
        PreparationResponseDTO dto = this.preparationController.markAsCompleted(command);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/waiting-list")
    public ResponseEntity<ItemsResponseDTO<PreparationResponseDTO>> waitingList() {
        ItemsResponseDTO<PreparationResponseDTO> dto = this.preparationController.getWaitingList();
        return ResponseEntity.ok(dto);
    }
}
