//package com.store.soattechchallenge.identification.presenters;
//
//import com.store.soattechchallenge.payment.domain.entities.Payment;
//import com.store.soattechchallenge.payment.infrastructure.api.dto.PaymentResponseDTO;
//import com.store.soattechchallenge.payment.infrastructure.mappers.PaymentMapper;
//
//public class IdentificationHttpPresenter {
//    private final PaymentMapper paymentMapper;
//
//    public IdentificationHttpPresenter(PaymentMapper paymentMapper) {
//        this.paymentMapper = paymentMapper;
//    }
//
//    public PaymentResponseDTO present(Payment payment) {
//        return this.paymentMapper.fromDomainToDTO(payment);
//    }
//}
