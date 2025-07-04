package com.store.soattechchallenge.preparation.infrastructure.jpa;

import com.store.soattechchallenge.preparation.domain.entites.Preparation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_preparacao")
public class JpaPreparation {
    @Id
    private String id;

    @Column(name = "posicao_preparacao")
    private Integer preparationPosition;

    @Column(name = "tempo_de_preparacao")
    private Integer preparationTime;

    @Column(name = "estimativa_de_pronto")
    private LocalDateTime estimatedReadyTime;

    @Column(name = "st_preparacao")
    private String preparationStatus;

    @Column(name = "dt_inclusao")
    private LocalDateTime createdAt;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public JpaPreparation() {
    }

    public JpaPreparation(Preparation preparation) {
        this.id = preparation.getId();
        this.preparationPosition = preparation.getPreparationPosition();
        this.preparationTime = preparation.getPreparationTime();
        this.estimatedReadyTime = preparation.getEstimatedReadyTime();
        this.preparationStatus = preparation.getPreparationStatus().toString();
        this.createdAt = preparation.getCreatedAt();
        this.timestamp = preparation.getTimestamp();
    }

    public String getId() {
        return id;
    }

    public Integer getPreparationPosition() {
        return preparationPosition;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public LocalDateTime getEstimatedReadyTime() {
        return estimatedReadyTime;
    }

    public String getPreparationStatus() {
        return preparationStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}