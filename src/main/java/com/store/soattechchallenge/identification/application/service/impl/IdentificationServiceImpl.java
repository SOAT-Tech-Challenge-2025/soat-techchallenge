package com.store.soattechchallenge.identification.application.service.impl;

import com.store.soattechchallenge.identification.application.usecases.IdentificationUseCases;
import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.model.IdentificationDTO;
import com.store.soattechchallenge.identification.domain.repository.IdentificationRepository;
import com.store.soattechchallenge.identification.infrastructure.adapters.out.model.JpaIdentification;
import com.store.soattechchallenge.utils.DocumentValidator;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class IdentificationServiceImpl implements IdentificationUseCases {

    private final IdentificationRepository identificationRepository;

    public IdentificationServiceImpl(IdentificationRepository identificationRepository) {
        this.identificationRepository = identificationRepository;
    }

//    @Autowired
//    private IdentificationMapper mapper;

    @Override
    public Identification createClient(IdentificationDTO identificationDTO) {

        if (!DocumentValidator.isValidCPF(identificationDTO.getNumberDocument())) {
            throw new CustomException(
                    "CPF inválido",
                    HttpStatus.BAD_REQUEST,
                    "040",
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }

        if (identificationRepository.existsByNumberDocument(identificationDTO.getNumberDocument())) {
            throw new CustomException(
                    "Cliente já cadastrado",
                    HttpStatus.BAD_REQUEST,
                    "020",
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }

        if (identificationRepository.existsByEmail(identificationDTO.getEmail())) {
            throw new CustomException(
                    "Email já cadastrado",
                    HttpStatus.BAD_REQUEST,
                    "030",
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }

        Identification mapperIdentification = new Identification();
        mapperIdentification.setNameClient(identificationDTO.getNameClient());
        mapperIdentification.setNumberDocument(identificationDTO.getNumberDocument());
        mapperIdentification.setEmail(identificationDTO.getEmail());
        mapperIdentification.setCreatedAt(LocalDateTime.now());
        identificationRepository.createClient(mapperIdentification);

        return mapperIdentification;
    }

    @Override
    public IdentificationDTO getByClient(UUID identification_id) {
        Optional<JpaIdentification> identification = identificationRepository.getByClient(identification_id);
        if (identification.isPresent()) {
            IdentificationDTO identificationDTO = new IdentificationDTO();
            identificationDTO.setNameClient(identification.get().getNameClient());
            identificationDTO.setNumberDocument(identification.get().getNumberDocument());
            identificationDTO.setEmail(identification.get().getEmail());
            identificationDTO.setCreatedAt(identification.get().getCreatedAt());
            identificationDTO.setUpdatedAt(identification.get().getUpdatedAt());
            return identificationDTO;
        } else {
            throw new CustomException(
                    "Cliente não encontrado",
                    HttpStatus.BAD_REQUEST,
                    "010",
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }
}