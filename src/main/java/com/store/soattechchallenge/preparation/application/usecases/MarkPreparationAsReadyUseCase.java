package com.store.soattechchallenge.preparation.application.usecases;

import com.store.soattechchallenge.preparation.application.gateways.PreparationGateway;
import com.store.soattechchallenge.preparation.application.usecases.commands.MarkPreparationAsReadyCommand;
import com.store.soattechchallenge.preparation.domain.entites.Preparation;
import com.store.soattechchallenge.preparation.application.gateways.exceptions.EntityNotFoundException;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class MarkPreparationAsReadyUseCase {
    private final PreparationGateway preparationGateway;

    public MarkPreparationAsReadyUseCase(PreparationGateway preparationGateway) {
        this.preparationGateway = preparationGateway;
    }

    public Preparation execute(MarkPreparationAsReadyCommand command) {
        try {
            Preparation preparation = this.preparationGateway.findById(command.id());
            preparation.ready();
            preparation = this.preparationGateway.save(preparation);
            return preparation;
        } catch (EntityNotFoundException error) {
            throw new CustomException(
                    "Preparation not found",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        } catch (IllegalArgumentException error) {
            throw new CustomException(
                    error.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }
}
