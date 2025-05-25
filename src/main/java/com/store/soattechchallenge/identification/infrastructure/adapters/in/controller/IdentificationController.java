package com.store.soattechchallenge.identification.infrastructure.adapters.in.controller;

import com.store.soattechchallenge.identification.application.service.impl.IdentificationServiceImpl;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.dto.IdentificationRequestDTO;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.model.JpaIdentification;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class IdentificationController {

    private final IdentificationServiceImpl service;

    public IdentificationController(IdentificationServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/identifications")
    public ResponseEntity<Void> createClient(@RequestBody @Valid IdentificationRequestDTO identificationRequestDTO) {
        service.createClient(identificationRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/identifications/{identification_id}")
    public ResponseEntity<Optional<JpaIdentification>> getByClient(@PathVariable String identification_id) {
        Optional<JpaIdentification> jpaIdentification = service.findByDocumentOrEmail(identification_id);
        return new ResponseEntity<>(jpaIdentification, HttpStatus.OK);
    }
}
