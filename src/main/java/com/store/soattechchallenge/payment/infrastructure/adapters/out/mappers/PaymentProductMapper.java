package com.store.soattechchallenge.payment.infrastructure.adapters.out.mappers;

import com.store.soattechchallenge.payment.domain.model.Product;
import com.store.soattechchallenge.payment.infrastructure.adapters.in.rest.dto.PaymentProductCreateRequestDTO;

public interface PaymentProductMapper {
    Product createDTOToDomain(PaymentProductCreateRequestDTO paymentProductCreateRequestDTO);
}
