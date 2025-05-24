package com.store.soattechchallenge.identification.infrastructure.adapters.in.controller;

import com.store.soattechchallenge.identification.application.service.impl.IdentificationServiceImpl;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.model.IdentificationDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class IdentificationController {

    private final IdentificationServiceImpl service;

    public IdentificationController(IdentificationServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/identifications")
    public ResponseEntity<Void> createClient(@RequestBody @Valid IdentificationDTO identificationDTO) {
        service.createClient(identificationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/identifications/{identification_id}")
    public ResponseEntity<IdentificationDTO> getByClient(@PathVariable UUID identification_id) {
        IdentificationDTO identificationDTO = service.getByClient(identification_id);
        return new ResponseEntity<>(identificationDTO, HttpStatus.OK);
    }
}
