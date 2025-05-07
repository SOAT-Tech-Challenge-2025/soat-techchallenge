package com.store.soattechchallenge.identification.domain.model;

import java.time.LocalDateTime;

public class IdentificationDTO {

    private String nameClient;

    private String numberDocument;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public IdentificationDTO() {
    }

    public IdentificationDTO(Long id, String nameClient, String numberDocument, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
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