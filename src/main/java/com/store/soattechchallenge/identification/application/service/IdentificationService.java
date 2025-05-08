package com.store.soattechchallenge.identification.application.service;

import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.adapters.driven.repository.IdentificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentificationService {


    @Autowired
    private IdentificationRepository identificationRepository;

    public List<Identification> getAllIdentifications() {
        return identificationRepository.findAll();
    }
}