package com.store.soattechchallenge.identification.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.domain.repository.IdentificationRepository;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.model.JpaIdentification;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.repository.JpaIdentificationRepository;
import com.store.soattechchallenge.utils.DocumentValidator;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class IdentificationRepositoryImpl implements IdentificationRepository {

    private final JpaIdentificationRepository jpaIdentificationRepository;

    public IdentificationRepositoryImpl(JpaIdentificationRepository jpaIdentificationRepository) {
        this.jpaIdentificationRepository = jpaIdentificationRepository;
    }

    @Override
    public Identification createClient(Identification identification) {
        JpaIdentification jpaIdentification = new JpaIdentification(identification);

        if (!DocumentValidator.isValidCPF(jpaIdentification.getNumberDocument())) {
            throw new CustomException(
                    "CPF inválido",
                    HttpStatus.BAD_REQUEST,
                    "040",
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }

        this.jpaIdentificationRepository.save(jpaIdentification);
        return new Identification(jpaIdentification.getId(), jpaIdentification.getNameClient(), jpaIdentification.getEmail(),
                jpaIdentification.getNumberDocument(), jpaIdentification.getCreatedAt(), jpaIdentification.getUpdatedAt());
    }

    @Override
    public Optional<JpaIdentification> findByDocumentOrEmail(String id_document, String id_email) {
        Optional<JpaIdentification> byDocumentOrEmail = jpaIdentificationRepository.findByDocumentOrEmail(id_document, id_email);
        if (byDocumentOrEmail.isPresent()) {
            return byDocumentOrEmail;
        } else {
            throw new CustomException(
                    "Cliente não encontrado",
                    HttpStatus.BAD_REQUEST,
                    "010",
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Override
    public boolean existsByNumberDocument(String numberDocument) {
        boolean byNumberDocument = jpaIdentificationRepository.existsByNumberDocument(numberDocument);
        if (byNumberDocument) {
            throw new CustomException(
                    "Cliente já cadastrado",
                    HttpStatus.BAD_REQUEST,
                    "020",
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        boolean byEmail = jpaIdentificationRepository.existsByEmail(email);
        if (byEmail) {
            throw new CustomException(
                    "Email já cadastrado",
                    HttpStatus.BAD_REQUEST,
                    "030",
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
        return false;
    }
}
