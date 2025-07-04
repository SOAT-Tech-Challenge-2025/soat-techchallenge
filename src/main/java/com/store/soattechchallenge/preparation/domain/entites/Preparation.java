package com.store.soattechchallenge.preparation.domain.entites;

import com.store.soattechchallenge.preparation.domain.PreparationStatus;

import java.time.LocalDateTime;

public class Preparation {
    private String id;
    private Integer preparationPosition;
    private Integer preparationTime;
    private LocalDateTime estimatedReadyTime;
    private PreparationStatus preparationStatus;
    private LocalDateTime createdAt;
    private LocalDateTime timestamp;

    public Preparation() {
        
    }

    public Preparation(
            String id,
            Integer preparationPosition,
            Integer preparationTime,
            LocalDateTime estimatedReadyTime,
            PreparationStatus preparationStatus,
            LocalDateTime createdAt,
            LocalDateTime timestamp
    ) {
        this.id = id;
        this.preparationPosition = preparationPosition;
        this.preparationTime = preparationTime;
        this.estimatedReadyTime = estimatedReadyTime;
        this.preparationStatus = preparationStatus;
        this.createdAt = createdAt;
        this.timestamp = timestamp;
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

    public PreparationStatus getPreparationStatus() {
        return preparationStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setPreparationPosition(Integer preparationPosition) {
        this.preparationPosition = preparationPosition;
    }

    public void setPreparationStatus(PreparationStatus preparationStatus) {
        this.preparationStatus = preparationStatus;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setEstimatedReadyTime(LocalDateTime estimatedReadyTime) {
        this.estimatedReadyTime = estimatedReadyTime;
    }

    public void ready() {
        if (!this.preparationStatus.equals(PreparationStatus.IN_PREPARATION)) {
            throw new IllegalArgumentException(
                    "A preparation with " + this.preparationStatus + " status cannot be updated to " +
                            PreparationStatus.READY
            );
        }

        this.preparationStatus = PreparationStatus.READY;
        this.timestamp = LocalDateTime.now();
    }

    public void complete() {
        if (!this.preparationStatus.equals(PreparationStatus.READY)) {
            throw new IllegalArgumentException(
                    "A preparation with " + this.preparationStatus + " status cannot be updated to " +
                            PreparationStatus.COMPLETED
            );
        }

        this.preparationStatus = PreparationStatus.COMPLETED;
        this.timestamp = LocalDateTime.now();
    }
}
