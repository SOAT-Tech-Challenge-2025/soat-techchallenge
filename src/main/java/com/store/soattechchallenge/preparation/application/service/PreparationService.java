package com.store.soattechchallenge.preparation.application.service;

import com.store.soattechchallenge.preparation.domain.exception.InvalidPreparationException;
import com.store.soattechchallenge.preparation.infrastructure.adapters.out.repository.exception.EntityNotFoundException;
import com.store.soattechchallenge.preparation.application.usecases.PreparationUseCase;
import com.store.soattechchallenge.preparation.domain.Notifier;
import com.store.soattechchallenge.preparation.domain.model.NotificationDestination;
import com.store.soattechchallenge.preparation.domain.model.NotificationMessage;
import com.store.soattechchallenge.preparation.domain.model.Preparation;
import com.store.soattechchallenge.preparation.domain.model.PreparationStatus;
import com.store.soattechchallenge.preparation.domain.repository.PreparationRepository;
import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PreparationService implements PreparationUseCase {
    private final PreparationRepository preparationRepository;
    private final Notifier notifier;

    public PreparationService(PreparationRepository preparationRepository, Notifier notifier) {
        this.preparationRepository = preparationRepository;
        this.notifier = notifier;
    }

    @Override
    public Preparation create(String id, Integer preparationTime) {
        if (this.preparationRepository.existsById(id)) {
            throw new CustomException(
                    "Preparation with ID " + id + " already exists",
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }

        Integer maxPosition = this.preparationRepository.findMaxPosition();
        LocalDateTime now = LocalDateTime.now();
        Preparation preparation = new Preparation(
                id,
                maxPosition + 1,
                preparationTime,
                null,
                PreparationStatus.RECEIVED,
                now,
                now
        );

        return this.preparationRepository.save(preparation);
    }

    @Override
    public Preparation startNext() {
        Optional<Preparation> optionalPreparation = this.preparationRepository.findReceivedWithMinPosition();
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
        preparation = this.preparationRepository.save(preparation);
        this.preparationRepository.decrementReceivedPositionsGreaterThan(oldPreparationPosition);
        return preparation;
    }

    @Override
    public Preparation ready(String id) {
        try {
            Preparation preparation = this.preparationRepository.findById(id);
            preparation.ready();
            preparation = this.preparationRepository.save(preparation);
            NotificationDestination notificationDestination = new NotificationDestination("test@email.com");
            NotificationMessage notificationMessage = new NotificationMessage(
                    "Seu pedido está pronto",
                    "O pedido " + preparation.getId() + " está disponível no balcão"
            );
            this.notifier.send(notificationDestination, notificationMessage);
            return preparation;
        } catch (EntityNotFoundException error) {
            throw new CustomException(
                    "Preparation not found",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        } catch (InvalidPreparationException error) {
            throw new CustomException(
                    error.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Override
    public Preparation finalize(String id) {
        try {
            Preparation preparation = this.preparationRepository.findById(id);
            preparation.complete();
            return this.preparationRepository.save(preparation);
        } catch (EntityNotFoundException error) {
            throw new CustomException(
                    "Preparation not found",
                    HttpStatus.NOT_FOUND,
                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        } catch (InvalidPreparationException error) {
            throw new CustomException(
                    error.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    LocalDateTime.now(),
                    UUID.randomUUID()
            );
        }
    }

    @Override
    public List<Preparation> waitingList() {
        List<Preparation> preparations = this.preparationRepository.getReadyWaitingList();
        preparations.addAll(this.preparationRepository.getInPreparationWaitingList());
        preparations.addAll(this.preparationRepository.getReceivedWaitingList());
        return preparations;
    }
}
