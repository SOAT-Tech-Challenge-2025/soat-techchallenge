package com.store.soattechchallenge.payment.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.payment.infrastructure.adapters.out.repository.exception.EntityNotFoundException;
import com.store.soattechchallenge.payment.domain.model.Payment;
import com.store.soattechchallenge.payment.domain.repository.PaymentRepository;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers.PaymentMapper;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.model.JpaPayment;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.repository.PaymentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentJpaRepository jpaRepository;
    private final PaymentMapper paymentMapper;

    public PaymentRepositoryImpl(
            PaymentJpaRepository paymentJpaRepository,
            PaymentMapper paymentMapper
    ) {
        this.jpaRepository = paymentJpaRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public Payment findById(String id) {
        Optional<JpaPayment> optionalJpaPayment = this.jpaRepository.findById(id);
        if (optionalJpaPayment.isEmpty()) {
            throw new EntityNotFoundException("Payment with ID " + id + " not found");
        }

        return this.paymentMapper.toDomain(optionalJpaPayment.get());
    }

    @Override
    public Boolean existsById(String id) {
        return this.jpaRepository.existsById(id);
    }

    @Override
    public Boolean existsByExternalId(String id) {
        return this.jpaRepository.existsByExternalId(id);
    }

    @Override
    public Payment save(Payment payment) {
        JpaPayment jpaPayment = this.jpaRepository.save(
                this.paymentMapper.toJPA(payment)
        );

        return this.paymentMapper.toDomain(jpaPayment);
    }
}
