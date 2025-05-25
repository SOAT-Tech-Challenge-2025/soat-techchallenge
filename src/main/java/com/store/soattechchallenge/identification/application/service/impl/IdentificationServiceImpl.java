package com.store.soattechchallenge.identification.application.service.impl;

import com.store.soattechchallenge.identification.application.usecases.IdentificationUseCases;
import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.domain.repository.IdentificationRepository;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.dto.IdentificationRequestDTO;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.model.JpaIdentification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class IdentificationServiceImpl implements IdentificationUseCases {

    private final IdentificationRepository identificationRepository;

    public IdentificationServiceImpl(IdentificationRepository identificationRepository) {
        this.identificationRepository = identificationRepository;
    }

    @Override
    public Identification createClient(IdentificationRequestDTO identificationRequestDTO) {
        identificationRepository.existsByNumberDocument(identificationRequestDTO.getNumberDocument());

        identificationRepository.existsByEmail(identificationRequestDTO.getEmail());

        Identification mapperIdentification = new Identification();
        mapperIdentification.setNameClient(identificationRequestDTO.getNameClient());
        mapperIdentification.setNumberDocument(identificationRequestDTO.getNumberDocument());
        mapperIdentification.setEmail(identificationRequestDTO.getEmail());
        mapperIdentification.setCreatedAt(LocalDateTime.now());
        mapperIdentification.setUpdatedAt(LocalDateTime.now());
        identificationRepository.createClient(mapperIdentification);

        return mapperIdentification;
    }

    @Override
    public Optional<JpaIdentification> findByDocumentOrEmail(String documentOrEmail) {
        return identificationRepository.findByDocumentOrEmail(documentOrEmail, documentOrEmail);
    }
}