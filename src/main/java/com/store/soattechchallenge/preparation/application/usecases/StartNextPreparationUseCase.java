package com.store.soattechchallenge.preparation.application.usecases;

import com.store.soattechchallenge.preparation.application.gateways.PreparationGateway;
import com.store.soattechchallenge.preparation.domain.PreparationStatus;
import com.store.soattechchallenge.preparation.domain.entites.Preparation;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class StartNextPreparationUseCase {
    private final PreparationGateway preparationGateway;

    public StartNextPreparationUseCase(PreparationGateway preparationGateway) {
        this.preparationGateway = preparationGateway;
    }

    public Preparation execute() {
        Optional<Preparation> optionalPreparation = this.preparationGateway.findReceivedWithMinPosition();
        if (optionalPreparation.isEmpty()) {
            throw new CustomException(
                    "No preparation available to start",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }

        Preparation preparation = optionalPreparation.get();
        Integer oldPreparationPosition = preparation.getPreparationPosition();
        Integer preparationTime = preparation.getPreparationTime() == null ? 0 : preparation.getPreparationTime();
        preparation.setPreparationPosition(null);
        preparation.setPreparationStatus(PreparationStatus.IN_PREPARATION);
        preparation.setEstimatedReadyTime(LocalDateTime.now().plusMinutes(preparationTime));
        preparation.setTimestamp(LocalDateTime.now());
        preparation = this.preparationGateway.save(preparation);
        this.preparationGateway.decrementReceivedPositionsGreaterThan(oldPreparationPosition);
        return preparation;
    }
}
