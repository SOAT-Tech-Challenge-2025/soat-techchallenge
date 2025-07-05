package com.store.soattechchallenge.preparation.application.usecases;

import com.store.soattechchallenge.preparation.application.gateways.PreparationRepositoryGateway;
import com.store.soattechchallenge.preparation.domain.PreparationStatus;
import com.store.soattechchallenge.preparation.domain.entites.Preparation;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class StartNextPreparationUseCase {
    private final PreparationRepositoryGateway preparationRepositoryGateway;

    public StartNextPreparationUseCase(PreparationRepositoryGateway preparationRepositoryGateway) {
        this.preparationRepositoryGateway = preparationRepositoryGateway;
    }

    public Preparation execute() {
        Optional<Preparation> optionalPreparation = this.preparationRepositoryGateway.findReceivedWithMinPosition();
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
        preparation = this.preparationRepositoryGateway.save(preparation);
        this.preparationRepositoryGateway.decrementReceivedPositionsGreaterThan(oldPreparationPosition);
        return preparation;
    }
}
