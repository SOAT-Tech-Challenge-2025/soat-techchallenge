package com.store.soattechchallenge.payment.gateways;

import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.model.MPOrder;
import com.store.soattechchallenge.payment.infrastructure.integrations.mercado_pago.model.MPPayment;

public interface MercadoPagoClientGateway {
    public MPOrder findOrderById(String id);
    public MPPayment findPaymentById(String id);
}
