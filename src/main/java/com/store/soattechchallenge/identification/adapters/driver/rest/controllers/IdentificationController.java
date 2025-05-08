package com.store.soattechchallenge.identification.adapters.driver.rest.controllers;

import com.store.soattechchallenge.identification.adapters.driver.rest.dto.IdentificationDTO;
import com.store.soattechchallenge.identification.application.service.IdentificationService;
import com.store.soattechchallenge.identification.domain.model.Identification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class IdentificationController {

    @Autowired
    private IdentificationService identificationService;


    @GetMapping("/identifications")
    public List<IdentificationDTO> getAllIdentifications() {
        return identificationService.getAllIdentifications()
                .stream()
                .map(identification -> new IdentificationDTO(
                     identification.getId(),
                    identification.getNameClient(),
                    identification.getNumberDocument(),
                    identification.getEmail(),
                    identification.getCreatedAt(),
                    identification.getUpdatedAt()))
                .collect(Collectors.toList());
    }
}
