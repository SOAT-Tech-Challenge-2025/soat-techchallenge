package com.store.soattechchallenge.identification.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaIdentificationRepository extends JpaRepository<JpaIdentification, UUID> {

    boolean existsByNumberDocument(String numberDocument);

    boolean existsByEmail(String email);

    @Query("SELECT i FROM JpaIdentification i WHERE i.numberDocument = :numberDocument OR i.email = :email")
    Optional<JpaIdentification> findByDocumentOrEmail(@Param("numberDocument") String numberDocument,
                                                      @Param("email") String email);
}