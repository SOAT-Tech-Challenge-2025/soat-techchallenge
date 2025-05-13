package com.store.soattechchallenge.identification.application.service.impl;

import com.store.soattechchallenge.identification.application.mapper.IdentificationMapper;
import com.store.soattechchallenge.identification.application.usecases.IdentificationUseCases;
import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.domain.model.IdentificationDTO;
import com.store.soattechchallenge.identification.domain.repository.IdentificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class IdentificationServiceImpl implements IdentificationUseCases {

    private final IdentificationRepository identificationRepository;

    public IdentificationServiceImpl(IdentificationRepository identificationRepository) {
        this.identificationRepository = identificationRepository;
    }

//    @Autowired
//    private IdentificationMapper mapper;

    @Override
    public Identification createClient(IdentificationDTO identificationDTO) {
        Identification mapperIdentification = new Identification();
        mapperIdentification.setNameClient(identificationDTO.getNameClient());
        mapperIdentification.setNumberDocument(identificationDTO.getNumberDocument());
        mapperIdentification.setEmail(identificationDTO.getEmail());
        mapperIdentification.setCreatedAt(LocalDateTime.now());
        identificationRepository.createClient(mapperIdentification);

        return mapperIdentification;
    }
}