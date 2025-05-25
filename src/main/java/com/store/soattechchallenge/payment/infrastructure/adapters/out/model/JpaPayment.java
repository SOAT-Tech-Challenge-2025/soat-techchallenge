package com.store.soattechchallenge.payment.infrastructure.adapters.out.model;

import com.store.soattechchallenge.payment.domain.model.Payment;
import com.store.soattechchallenge.payment.domain.model.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pagamento")
public class JpaPayment {
    @Id
    private String id;

    @Column(name = "id_externo", unique = true)
    private String externalId;

    @Column(name = "st_pagamento")
    private PaymentStatus paymentStatus;

    @Column(name = "vl_total_pedido")
    private Double totalOrderValue;

    @Column(name = "codigo_qr")
    private String qrCode;

    @Column(name = "expiracao")
    private LocalDateTime expiration;

    @Column(name = "dt_inclusao")
    private LocalDateTime createdAt;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public JpaPayment() {

    }

    public JpaPayment(Payment payment) {
        this.id = payment.getId();
        this.externalId = payment.getExternalId();
        this.paymentStatus = payment.getPaymentStatus();
        this.totalOrderValue = payment.getTotalOrderValue();
        this.qrCode = payment.getQrCode();
        this.expiration = payment.getExpiration();
        this.createdAt = payment.getCreatedAt();
        this.timestamp = payment.getTimestamp();
    }

    public String getId() {
        return id;
    }

    public String getExternalId() {
        return externalId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Double getTotalOrderValue() {
        return totalOrderValue;
    }

    public String getQrCode() {
        return qrCode;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
