package com.store.soattechchallenge.payment.infrastructure.gateways;

import com.store.soattechchallenge.payment.gateways.MercadoPagoClientGateway;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.MercadoPagoClient;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.model.MPOrder;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.model.MPPayment;

public class MercadoPagoAPIClientGateway implements MercadoPagoClientGateway {
    private final MercadoPagoClient mercadoPagoClient;

    public MercadoPagoAPIClientGateway(MercadoPagoClient mercadoPagoClient) {
        this.mercadoPagoClient = mercadoPagoClient;
    }

    @Override
    public MPOrder findOrderById(String id) {
        return this.mercadoPagoClient.findOrderById(id);
    }

    @Override
    public MPPayment findPaymentById(String id) {
        return this.mercadoPagoClient.findPaymentById(id);
    }
}
