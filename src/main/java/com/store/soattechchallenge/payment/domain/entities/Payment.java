package com.store.soattechchallenge.payment.domain.entities;

import com.store.soattechchallenge.payment.domain.PaymentStatus;

import java.time.LocalDateTime;

public class Payment {
    private String id;
    private String externalId;
    private PaymentStatus paymentStatus;
    private Double totalOrderValue;
    private String qrCode;
    private LocalDateTime expiration;
    private LocalDateTime createdAt;
    private LocalDateTime timestamp;

    public Payment(
            String id,
            String externalId,
            PaymentStatus paymentStatus,
            Double totalOrderValue,
            String qrCode,
            LocalDateTime expiration,
            LocalDateTime createdAt,
            LocalDateTime timestamp
    ) {
        this.id = id;
        this.externalId = externalId;
        this.paymentStatus = paymentStatus;
        this.totalOrderValue = totalOrderValue;
        this.qrCode = qrCode;
        this.expiration = expiration;
        this.createdAt = createdAt;
        this.timestamp = timestamp;
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

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void finalize(PaymentStatus paymentStatus) {
        this.checkIfIsValidToFinalize(paymentStatus);
        this.paymentStatus = paymentStatus;
        this.timestamp = LocalDateTime.now();
    }

    private void checkIfIsValidToFinalize(PaymentStatus newPaymentStatus) {
        boolean isValidStatus = this.paymentStatus.equals(PaymentStatus.OPENED);
        boolean isValidNewStatus = !newPaymentStatus.equals(PaymentStatus.OPENED);
        if (!isValidStatus || !isValidNewStatus) {
            throw new IllegalArgumentException(
                    "Unable to update a payment status from " + this.paymentStatus + " to " + newPaymentStatus
            );
        }
    }
}
