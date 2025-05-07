package com.store.soattechchallenge.identification.domain.repository.impl;

import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.domain.repository.IdentificationRepository;
import com.store.soattechchallenge.identification.infrastructure.adapter.out.repository.JpaIdentificationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class IdentificationRepositoryImpl implements IdentificationRepository {

    @Autowired
    private JpaIdentificationRepository jpaIdentificationRepository;

    @Override
    public List<Identification> findAll() {
        return this.jpaIdentificationRepository.findAll().stream().map(identificationJpa -> new Identification(
                identificationJpa.getId(),
                identificationJpa.getNameClient(),
                identificationJpa.getNumberDocument(),
                identificationJpa.getEmail(),
                identificationJpa.getCreatedAt(),
                identificationJpa.getUpdatedAt()))
                .collect(Collectors.toList());

    }
}
