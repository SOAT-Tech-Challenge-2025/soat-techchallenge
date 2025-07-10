package com.store.soattechchallenge.payment.domain.entities;

import com.store.soattechchallenge.payment.domain.PaymentStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class PaymentTest {
    private static final LocalDateTime FIXED_TIME = LocalDateTime.parse("2025-10-01T19:00:00");

    @Test
    void shouldFinalizePaymentWhenTheCurrentStatusIsOpenedAndTheNewStatusIsClosed() {
        Payment payment = new Payment(
                "PAYMENT-TEST-01",
                "PAYMENT-TEST-01",
                PaymentStatus.OPENED,
                60.0,
                "XXXXX",
                FIXED_TIME,
                FIXED_TIME,
                FIXED_TIME
        );

        payment.finalize(PaymentStatus.CLOSED);
        Assertions.assertEquals(PaymentStatus.CLOSED, payment.getPaymentStatus());
    }

    @Test
    void shouldNotFinalizePaymentWhenTheCurrentStatusIsNotOpened() {
        Payment payment = new Payment(
                "PAYMENT-TEST-01",
                "PAYMENT-TEST-01",
                PaymentStatus.CLOSED,
                60.0,
                "XXXXX",
                FIXED_TIME,
                FIXED_TIME,
                FIXED_TIME
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            payment.finalize(PaymentStatus.CLOSED);
        },  "Unable to update a payment status from CLOSED to CLOSED");
    }

    @Test
    void shouldNotFinalizePaymentWhenTheNewStatusIsNotClosed() {
        Payment payment = new Payment(
                "PAYMENT-TEST-01",
                "PAYMENT-TEST-01",
                PaymentStatus.OPENED,
                60.0,
                "XXXXX",
                FIXED_TIME,
                FIXED_TIME,
                FIXED_TIME
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            payment.finalize(PaymentStatus.OPENED);
        },  "Unable to update a payment status from OPENED to OPENED");
    }
}
