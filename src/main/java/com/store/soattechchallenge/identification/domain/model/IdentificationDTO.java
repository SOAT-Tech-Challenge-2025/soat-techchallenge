package com.store.soattechchallenge.identification.domain.model;

public class IdentificationDTO {

    private String nameClient;

    private String numberDocument;

    private String email;


    public IdentificationDTO() {
    }

    public IdentificationDTO(Long id, String nameClient, String numberDocument, String email) {
        this.nameClient = nameClient;
        this.numberDocument = numberDocument;
        this.email = email;
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
}