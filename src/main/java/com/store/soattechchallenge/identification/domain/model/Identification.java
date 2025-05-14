package com.store.soattechchallenge.identification.domain.model;

import java.time.LocalDateTime;

public class Identification {

    private Long id;

    private String nameClient;

    private String numberDocument;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Identification() {
    }

    public Identification(Long id, String nameClient, String numberDocument, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nameClient = nameClient;
        this.numberDocument = numberDocument;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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