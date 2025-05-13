package com.store.soattechchallenge.identification.infrastructure.adapters.in.controller;

import com.store.soattechchallenge.identification.application.service.impl.IdentificationServiceImpl;
import com.store.soattechchallenge.identification.domain.model.IdentificationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdentificationController {

    private final IdentificationServiceImpl service;

    public IdentificationController(IdentificationServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/identification")
    public ResponseEntity<Void> createClient(@RequestBody IdentificationDTO identificationDTO) {
        service.createClient(identificationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
