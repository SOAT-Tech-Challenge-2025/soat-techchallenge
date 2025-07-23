package com.store.soattechchallenge.identification.infrastructure.api.controller;

import com.store.soattechchallenge.identification.usecases.GetClientUseCase;
import com.store.soattechchallenge.identification.infrastructure.api.dto.LoginRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationAPIController {

    private final GetClientUseCase getClientUseCase;

    public AuthenticationAPIController(GetClientUseCase getClientUseCase) {
        this.getClientUseCase = getClientUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String authenticatedUser = getClientUseCase.authenticate(loginRequestDTO.getDocumentNumber(), loginRequestDTO.getEmail());
        return ResponseEntity.ok(Map.of("token", authenticatedUser));
    }
}