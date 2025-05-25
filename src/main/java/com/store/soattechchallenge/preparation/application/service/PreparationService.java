package com.store.soattechchallenge.preparation.application.service;

import com.store.soattechchallenge.preparation.application.usecases.PreparationUseCase;
import com.store.soattechchallenge.preparation.domain.Notifier;
import com.store.soattechchallenge.preparation.domain.exception.NoPreparationToStartException;
import com.store.soattechchallenge.preparation.domain.exception.PreparationAlreadyExistsException;
import com.store.soattechchallenge.preparation.domain.exception.InvalidPreparationException;
import com.store.soattechchallenge.preparation.domain.model.NotificationDestination;
import com.store.soattechchallenge.preparation.domain.model.NotificationMessage;
import com.store.soattechchallenge.preparation.domain.model.Preparation;
import com.store.soattechchallenge.preparation.domain.model.PreparationStatus;
import com.store.soattechchallenge.preparation.domain.repository.PreparationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PreparationService implements PreparationUseCase {
    private PreparationRepository preparationRepository;
    private Notifier notifier;

    public PreparationService(PreparationRepository preparationRepository, Notifier notifier) {
        this.preparationRepository = preparationRepository;
        this.notifier = notifier;
    }

    @Override
    public Preparation create(String id, Integer preparationTime) {
        if (this.preparationRepository.existsById(id)) {
            throw new PreparationAlreadyExistsException("Preparation with ID " + id + " already exists");
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
            throw new NoPreparationToStartException("No preparation available to start");
        }

        Preparation preparation = optionalPreparation.get();
        Integer oldPreparationPosition = preparation.getPreparationPosition();
        Integer preparationTime = preparation.getPreparationTime() == null ? 0 : preparation.getPreparationTime();
        preparation.setPreparationPosition(null);
        preparation.setPreparationStatus(PreparationStatus.IN_PREPARATION);
        preparation.setEstimatedReadyTime(LocalDateTime.now().plusSeconds(preparationTime));
        preparation.setTimestamp(LocalDateTime.now());
        preparation = this.preparationRepository.save(preparation);
        this.preparationRepository.decrementReceivedPositionsGreaterThan(oldPreparationPosition);
        return preparation;
    }

    @Override
    public Preparation ready(String id) {
        Preparation preparation = this.preparationRepository.findById(id);
        if (!preparation.getPreparationStatus().equals(PreparationStatus.IN_PREPARATION)) {
            throw new InvalidPreparationException(
                    "A preparation with " + preparation.getPreparationStatus() + " status cannot be updated to " +
                            PreparationStatus.READY
            );
        }

        preparation.setPreparationStatus(PreparationStatus.READY);
        preparation.setTimestamp(LocalDateTime.now());
        preparation = this.preparationRepository.save(preparation);
        NotificationDestination notificationDestination = new NotificationDestination("test@email.com");
        NotificationMessage notificationMessage = new NotificationMessage(
                "Seu pedido está pronto",
                "O pedido " + preparation.getId() + " está disponível no balcão"
        );
        this.notifier.send(notificationDestination, notificationMessage);
        return preparation;
    }

    @Override
    public Preparation finalize(String id) {
        Preparation preparation = this.preparationRepository.findById(id);
        if (!preparation.getPreparationStatus().equals(PreparationStatus.READY)) {
            throw new InvalidPreparationException(
                    "A preparation with " + preparation.getPreparationStatus() + " status cannot be updated to " +
                            PreparationStatus.COMPLETED
            );
        }
        preparation.setPreparationStatus(PreparationStatus.COMPLETED);
        preparation.setTimestamp(LocalDateTime.now());
        return this.preparationRepository.save(preparation);
    }

    @Override
    public List<Preparation> waitingList() {
        List<Preparation> preparations = this.preparationRepository.getReadyWaitingList();
        preparations.addAll(this.preparationRepository.getInPreparationWaitingList());
        preparations.addAll(this.preparationRepository.getReceivedWaitingList());
        return preparations;
    }
}
