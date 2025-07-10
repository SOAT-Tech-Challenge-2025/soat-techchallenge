package com.store.soattechchallenge.payment.application.gateways.exceptions;

  public class PaymentCreationException extends RuntimeException {
  public PaymentCreationException(String message) {
    super(message);
  }
}
