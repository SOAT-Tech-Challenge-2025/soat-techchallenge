package com.store.soattechchallenge.payment.application.usecases;

import com.store.soattechchallenge.payment.domain.model.Payment;
import com.store.soattechchallenge.payment.domain.model.Product;

import java.awt.image.BufferedImage;
import java.util.List;

public interface PaymentUseCase {
    public Payment find (String id);
    public Payment create (String id, Double vlTotalPedido, List<Product> products);
    public Payment finalizeByMercadoPagoPaymentId (String paymentId);
    public BufferedImage renderCodigoQr(String id, int width, int height);
}
