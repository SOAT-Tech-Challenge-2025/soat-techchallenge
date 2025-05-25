package com.store.soattechchallenge.payment.domain.repository;

import com.store.soattechchallenge.payment.domain.model.Payment;

public interface PaymentRepository {
    public Payment findById (String id);
    public Boolean existsById(String id);
    public Boolean existsByExternalId(String id);
    public Payment save (Payment payment);
}
