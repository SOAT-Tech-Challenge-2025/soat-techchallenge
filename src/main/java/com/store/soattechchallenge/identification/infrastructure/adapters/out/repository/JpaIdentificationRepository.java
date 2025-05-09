package com.store.soattechchallenge.identification.infrastructure.adapter.out.repository;

import com.store.soattechchallenge.identification.infrastructure.adapter.out.model.IdentificationJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaIdentificationRepository extends JpaRepository<IdentificationJpa, Long> {
}
