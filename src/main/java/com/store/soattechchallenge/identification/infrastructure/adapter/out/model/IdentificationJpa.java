package com.store.soattechchallenge.identification.infrastructure.adapter.out.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "identificacao")
@Entity
public class IdentificationJpa {

    @Id
    @GeneratedValue
    private Long id;

    private String nameClient;

    private String numberDocument;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}