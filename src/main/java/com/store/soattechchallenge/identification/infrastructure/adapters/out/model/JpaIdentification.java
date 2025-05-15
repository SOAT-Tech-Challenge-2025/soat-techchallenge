package com.store.soattechchallenge.identification.infrastructure.adapters.out.model;

import com.store.soattechchallenge.identification.domain.model.Identification;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_cliente")
@Entity
@Builder
public class JpaIdentification {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "nm_cliente")
    private String nameClient;

    @Column(name = "ds_email")
    private String email;

    @Column(name = "nr_documento")
    private String numberDocument;

    @Column(name = "dt_inclusao")
    private LocalDateTime createdAt;

    @Column(name = "timestamp")
    private LocalDateTime updatedAt;

    public JpaIdentification(Identification identification) {
        this.id = identification.getId();
        this.nameClient = identification.getNameClient();
        this.email = identification.getEmail();
        this.numberDocument = identification.getNumberDocument();
        this.createdAt = identification.getCreatedAt();
        this.updatedAt = identification.getUpdatedAt();
    }

    public JpaIdentification() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberDocument() {
        return numberDocument;
    }

    public void setNumberDocument(String numberDocument) {
        this.numberDocument = numberDocument;
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
