package com.store.soattechchallenge.payment.application.usecases.commands;

import com.store.soattechchallenge.payment.application.gateways.PaymentProductDTO;

import java.util.List;

public record CreatePaymentCommand(String id, Double vlTotalPedido, List<PaymentProductDTO> products) {
}
