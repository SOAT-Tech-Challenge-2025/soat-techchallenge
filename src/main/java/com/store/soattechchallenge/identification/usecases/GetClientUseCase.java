package com.store.soattechchallenge.identification.usecases;

import com.store.soattechchallenge.identification.gateways.IdentificationRepositoryGateway;
import com.store.soattechchallenge.identification.domain.entities.Identification;
import com.store.soattechchallenge.identification.infrastructure.security.JwtTokenSecurity;

import java.util.Optional;

public class GetClientUseCase {

    private final IdentificationRepositoryGateway identificationRepositoryGateway;

    private final JwtTokenSecurity jwtTokenSecurity;

    public GetClientUseCase(IdentificationRepositoryGateway identificationRepositoryGateway, JwtTokenSecurity jwtTokenSecurity) {
        this.identificationRepositoryGateway = identificationRepositoryGateway;
        this.jwtTokenSecurity = jwtTokenSecurity;
    }

    public Optional<Identification> findByDocumentOrEmail(String documentOrEmail) {
        return identificationRepositoryGateway.findByDocumentOrEmail(documentOrEmail, documentOrEmail);
    }

    public String authenticate(String documentNumber, String email) {
        Optional<Identification> user = identificationRepositoryGateway.findByDocumentOrEmail(documentNumber, email);

        Identification identification = new Identification();
        identification.setId(user.get().getId());
        identification.setEmail(user.get().getEmail());
        identification.setNumberDocument(user.get().getNumberDocument());

        return jwtTokenSecurity.generateToken(identification);
    }
}
