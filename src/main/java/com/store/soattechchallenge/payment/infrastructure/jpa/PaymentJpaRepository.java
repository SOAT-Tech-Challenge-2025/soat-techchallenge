package com.store.soattechchallenge.payment.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentJpaRepository extends JpaRepository<JpaPayment, String> {
    public Optional<JpaPayment> findByExternalId(String externalId);
    public Boolean existsByExternalId(String externalId);
}
