package com.store.soattechchallenge.identification.application.service.impl;

import com.store.soattechchallenge.identification.application.usecases.IdentificationUseCases;
import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.domain.repository.IdentificationRepository;
import com.store.soattechchallenge.identification.domain.repository.impl.IdentificationRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentificationServiceImpl implements IdentificationUseCases {


    @Autowired
    private IdentificationRepositoryImpl identificationRepositoryImpl;

    @Override
    public List<Identification> getAllIdentifications() {
        return identificationRepositoryImpl.findAll();
    }
}