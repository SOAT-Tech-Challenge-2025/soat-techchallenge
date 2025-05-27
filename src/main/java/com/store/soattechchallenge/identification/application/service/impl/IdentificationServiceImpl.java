package com.store.soattechchallenge.identification.application.service.impl;

import com.store.soattechchallenge.identification.application.usecases.IdentificationUseCases;
import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.domain.repository.IdentificationRepository;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.dto.IdentificationRequestDTO;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.model.JpaIdentification;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.security.JwtTokenSecurity;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class IdentificationServiceImpl implements IdentificationUseCases {

    private final IdentificationRepository identificationRepository;

    private final JwtTokenSecurity jwtTokenSecurity;

    public IdentificationServiceImpl(IdentificationRepository identificationRepository, JwtTokenSecurity jwtTokenSecurity) {
        this.identificationRepository = identificationRepository;
        this.jwtTokenSecurity = jwtTokenSecurity;
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
    public Optional<Identification> findByDocumentOrEmail(String documentOrEmail) {
        return identificationRepository.findByDocumentOrEmail(documentOrEmail, documentOrEmail);
    }

    public String authenticate(String documentNumber, String email) {
        Optional<Identification> user = identificationRepository.findByDocumentOrEmail(documentNumber, email);

        Identification identification = new Identification();
        identification.setEmail(user.get().getEmail());
        identification.setNumberDocument(user.get().getNumberDocument());

        return jwtTokenSecurity.generateToken(identification);
    }
}