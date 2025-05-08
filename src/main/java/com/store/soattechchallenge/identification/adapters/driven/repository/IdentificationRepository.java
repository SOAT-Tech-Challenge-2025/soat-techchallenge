package com.store.soattechchallenge.identification.adapters.driven.repository;

import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.adapters.driven.jpa.repository.JpaIdentificationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class IdentificationRepository implements com.store.soattechchallenge.identification.domain.repository.IdentificationRepository {

    @Autowired
    private JpaIdentificationRepository jpaIdentificationRepository;

    @Override
    public List<Identification> findAll() {
        return this.jpaIdentificationRepository
                .findAll()
                .stream()
                .map(identificationJpa -> new Identification(
                    identificationJpa.getId(),
                    identificationJpa.getNameClient(),
                    identificationJpa.getNumberDocument(),
                    identificationJpa.getEmail(),
                    identificationJpa.getCreatedAt(),
                    identificationJpa.getUpdatedAt()))
                .collect(Collectors.toList());
    }
}
