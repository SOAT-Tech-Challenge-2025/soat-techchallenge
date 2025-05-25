package com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations;

import com.store.soattechchallenge.payment.configuration.MercadoPagoConfiguration;
import com.store.soattechchallenge.payment.domain.PaymentGateway;
import com.store.soattechchallenge.payment.domain.exception.PaymentCreationException;
import com.store.soattechchallenge.payment.domain.model.Payment;
import com.store.soattechchallenge.payment.domain.model.Product;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.MercadoPagoClient;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.exception.MPClientException;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.integrations.mercado_pago.model.*;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MercadoPagoPaymentGateway implements PaymentGateway {
    private final String USER_ID;
    private final String POS;
    private final String CALLBACK_URL;
    private final MercadoPagoClient mercadoPagoClient;

    public MercadoPagoPaymentGateway(
            MercadoPagoConfiguration mercadoPagoConfiguration,
            MercadoPagoClient mercadoPagoClient
    ) {
        this.USER_ID = mercadoPagoConfiguration.getUserId();
        this.POS = mercadoPagoConfiguration.getPos();
        this.CALLBACK_URL = mercadoPagoConfiguration.getCallbackUrl() + "?apikey=" +
                mercadoPagoConfiguration.getWebhookToken();

        this.mercadoPagoClient = mercadoPagoClient;
    }

    @Override
    public Payment create(Payment payment, List<Product> products) {
        List<MPItem> MPItems = products.stream()
                .map(product -> new MPItem(
                        product.getName(),
                        product.getCategory(),
                        product.getQuantity(),
                        "unit",
                        product.getUnitPrice(),
                        product.getTotalValue()
                ))
                .collect(Collectors.toList());

        ZoneOffset offset = OffsetDateTime.now().getOffset();
        OffsetDateTime offsetDateTime = payment.getExpiration().atOffset(offset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String description = "Order #" + payment.getId();
        MPCreateOrderRequest mpCreateOrderRequest = new MPCreateOrderRequest(
                payment.getId(),
                payment.getTotalOrderValue(),
                description,
                description,
                offsetDateTime.format(formatter),
                MPItems,
                this.CALLBACK_URL
        );

        try {
            MPCreateOrderResponse MPCreateOrderResponse = this.mercadoPagoClient.createDynamicQROrder(
                    this.USER_ID, this.POS, mpCreateOrderRequest
            );

            payment.setQrCode(MPCreateOrderResponse.qrData());
            return payment;
        } catch (MPClientException error) {
            throw new PaymentCreationException("Error creating payment");
        }
    }
}
