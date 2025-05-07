package com.store.soattechchallenge.identification.domain.repository;

import com.store.soattechchallenge.identification.domain.model.Identification;

import java.util.List;

public interface IdentificationRepository {

    List<Identification> findAll();
}
