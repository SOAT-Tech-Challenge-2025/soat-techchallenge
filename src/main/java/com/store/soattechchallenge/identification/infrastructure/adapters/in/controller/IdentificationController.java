package com.store.soattechchallenge.identification.infrastructure.adapter.in.controller;

import com.store.soattechchallenge.identification.application.service.impl.IdentificationServiceImpl;
import com.store.soattechchallenge.identification.domain.model.Identification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IdentificationController {

    @Autowired
    private IdentificationServiceImpl identificationService;


    @GetMapping("/identifications")
    public List<Identification> getAllIdentifications() {
        return identificationService.getAllIdentifications();
    }
}
