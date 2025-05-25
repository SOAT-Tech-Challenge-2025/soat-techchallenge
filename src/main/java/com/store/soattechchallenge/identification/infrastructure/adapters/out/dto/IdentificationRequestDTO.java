package com.store.soattechchallenge.identification.infrastructure.adapters.out.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class IdentificationRequestDTO {

    @NotBlank(message = "Nome do cliente não pode ser nulo ou vazio")
    private String nameClient;

    @NotBlank(message = "Número do documento não pode ser nulo ou vazio")
    private String numberDocument;

    @Email
    @NotBlank(message = "Email não pode ser nulo ou vazio")
    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public IdentificationRequestDTO() {
    }

    public IdentificationRequestDTO(String nameClient, String numberDocument, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.nameClient = nameClient;
        this.numberDocument = numberDocument;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getNumberDocument() {
        return numberDocument;
    }

    public void setNumberDocument(String numberDocument) {
        this.numberDocument = numberDocument;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}