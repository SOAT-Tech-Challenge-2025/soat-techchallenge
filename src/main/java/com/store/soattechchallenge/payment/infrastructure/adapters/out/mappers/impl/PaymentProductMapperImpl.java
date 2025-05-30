package com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers.impl;

import com.store.soattechchallenge.payment.domain.model.Product;
import com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto.PaymentProductCreateRequestDTO;
import com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers.PaymentProductMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentProductMapperImpl implements PaymentProductMapper {
    @Override
    public Product createDTOToDomain(PaymentProductCreateRequestDTO paymentProductCreateRequestDTO) {
        return new Product(
                paymentProductCreateRequestDTO.name(),
                paymentProductCreateRequestDTO.category(),
                paymentProductCreateRequestDTO.unitPrice(),
                paymentProductCreateRequestDTO.quantity()
        );
    }
}
