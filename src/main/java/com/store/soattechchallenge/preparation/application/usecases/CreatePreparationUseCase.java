package com.store.soattechchallenge.preparation.application.usecases;

import com.store.soattechchallenge.preparation.application.gateways.PreparationGateway;
import com.store.soattechchallenge.preparation.application.usecases.commands.CreatePreparationCommand;
import com.store.soattechchallenge.preparation.domain.PreparationStatus;
import com.store.soattechchallenge.preparation.domain.entites.Preparation;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreatePreparationUseCase {
    private final PreparationGateway preparationGateway;

    public CreatePreparationUseCase(PreparationGateway preparationGateway) {
        this.preparationGateway = preparationGateway;
    }

    public Preparation execute(CreatePreparationCommand command) {
        if (this.preparationGateway.existsById(command.id())) {
            throw new CustomException(
                    "Preparation with ID " + command.id() + " already exists",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }

        Integer maxPosition = this.preparationGateway.findMaxPosition();
        LocalDateTime now = LocalDateTime.now();
        Preparation preparation = new Preparation(
                command.id(),
                maxPosition + 1,
                command.preparationTime(),
                null,
                PreparationStatus.RECEIVED,
                now,
                now
        );

        return this.preparationGateway.save(preparation);
    }
}
