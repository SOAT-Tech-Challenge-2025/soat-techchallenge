package com.store.soattechchallenge.identification.infrastructure.adapters.in.controller;

import com.store.soattechchallenge.identification.application.service.impl.IdentificationServiceImpl;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.dto.LoginRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final IdentificationServiceImpl authenticationService;

    public AuthenticationController(IdentificationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String authenticatedUser = authenticationService.authenticate(loginRequestDTO.getDocumentNumber(), loginRequestDTO.getEmail());
        return ResponseEntity.ok(Map.of("token", authenticatedUser));
    }
}