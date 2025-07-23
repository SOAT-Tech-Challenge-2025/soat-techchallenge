package com.store.soattechchallenge.identification.infrastructure.api.controller;

import com.store.soattechchallenge.identification.usecases.commands.CreateClientCommand;
import com.store.soattechchallenge.identification.controller.IdentificationController;
import com.store.soattechchallenge.identification.domain.entities.Identification;
import com.store.soattechchallenge.identification.infrastructure.api.dto.IdentificationRequestDTO;
import com.store.soattechchallenge.identification.infrastructure.mappers.IdentificationMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class IdentificationAPIController {

    private final IdentificationController identificationController;
    private final IdentificationMapper identificationMapper;

    public IdentificationAPIController(
            IdentificationController identificationController,
            IdentificationMapper identificationMapper
    ) {
        this.identificationController = identificationController;
        this.identificationMapper = identificationMapper;
    }

    @PostMapping("/identifications")
    public ResponseEntity<Void> createClient(@RequestBody @Valid IdentificationRequestDTO identificationRequestDTO) {

        CreateClientCommand command = new CreateClientCommand(
                identificationRequestDTO.getNameClient(),
                identificationRequestDTO.getNumberDocument(),
                identificationRequestDTO.getEmail()
        );

        this.identificationController.create(command);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/identifications/{identification_id}")
    public ResponseEntity<Optional<Identification>> getByClient(@PathVariable String identification_id) {

        Optional<Identification> identification = identificationController.getClient(identification_id);
        return new ResponseEntity<>(identification, HttpStatus.OK);
    }
}
