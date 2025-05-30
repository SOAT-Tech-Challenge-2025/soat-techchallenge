package com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago;

import com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.model.MPCreateOrderRequest;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.model.MPCreateOrderResponse;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.model.MPOrder;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.model.MPPayment;

public interface MercadoPagoClient {
    public MPCreateOrderResponse createDynamicQROrder (
            String userId,
            String pos,
            MPCreateOrderRequest MPCreateOrderRequest
    );

    public MPOrder findOrderById(String id);
    public MPPayment findPaymentById(String id);
}
