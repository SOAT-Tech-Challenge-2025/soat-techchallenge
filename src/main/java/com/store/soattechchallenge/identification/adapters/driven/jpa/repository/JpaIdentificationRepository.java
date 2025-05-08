package com.store.soattechchallenge.identification.adapters.driven.jpa.repository;

import com.store.soattechchallenge.identification.adapters.driven.jpa.model.IdentificationJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaIdentificationRepository extends JpaRepository<IdentificationJpa, Long> {
}
