package com.store.soattechchallenge.identification.infrastructure.adapters.out.repository;

import com.store.soattechchallenge.identification.infrastructure.adapters.out.model.JpaIdentification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaIdentificationRepository extends JpaRepository<JpaIdentification, UUID> {

    boolean existsByNumberDocument(String numberDocument);

    boolean existsByEmail(String email);
}
