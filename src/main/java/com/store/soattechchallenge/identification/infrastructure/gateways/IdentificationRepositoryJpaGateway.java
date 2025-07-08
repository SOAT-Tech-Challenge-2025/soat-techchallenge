package com.store.soattechchallenge.identification.infrastructure.gateways;

import com.store.soattechchallenge.identification.application.gateways.IdentificationRepositoryGateway;
import com.store.soattechchallenge.identification.domain.entities.Identification;
import com.store.soattechchallenge.identification.infrastructure.jpa.JpaIdentification;
import com.store.soattechchallenge.identification.infrastructure.jpa.JpaIdentificationRepository;
import com.store.soattechchallenge.utils.DocumentValidator;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class IdentificationRepositoryJpaGateway implements IdentificationRepositoryGateway {

    JpaIdentificationRepository jpaIdentificationRepository;

    public IdentificationRepositoryJpaGateway(
            JpaIdentificationRepository jpaIdentificationRepository
    ) {
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
    public Optional<Identification> findByDocumentOrEmail(String id_document, String id_email) {
        Optional<JpaIdentification> byDocumentOrEmail = jpaIdentificationRepository.findByDocumentOrEmail(id_document, id_email);
        if (byDocumentOrEmail.isPresent()) {
            return Optional.of(new Identification(byDocumentOrEmail.get().getId(), byDocumentOrEmail.get().getNameClient(),
                    byDocumentOrEmail.get().getEmail(), byDocumentOrEmail.get().getNumberDocument(),
                    byDocumentOrEmail.get().getCreatedAt(), byDocumentOrEmail.get().getUpdatedAt()));
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
