package com.store.soattechchallenge.preparation.controller;

import com.store.soattechchallenge.preparation.application.usecases.CreatePreparationUseCase;
import com.store.soattechchallenge.preparation.application.usecases.MarkPreparationAsReadyUseCase;
import com.store.soattechchallenge.preparation.application.usecases.MarkPreparationAsCompletedUseCase;
import com.store.soattechchallenge.preparation.application.usecases.StartNextPreparationUseCase;
import com.store.soattechchallenge.preparation.application.usecases.GetWaitingListUseCase;
import com.store.soattechchallenge.preparation.application.usecases.commands.CreatePreparationCommand;
import com.store.soattechchallenge.preparation.application.usecases.commands.MarkPreparationAsCompletedCommand;
import com.store.soattechchallenge.preparation.application.usecases.commands.MarkPreparationAsReadyCommand;
import com.store.soattechchallenge.preparation.domain.entites.Preparation;

import java.util.List;

public class PreparationController {
    private final CreatePreparationUseCase createUseCase;
    private final StartNextPreparationUseCase startNextUseCase;
    private final MarkPreparationAsReadyUseCase markAsReadyUseCase;
    private final MarkPreparationAsCompletedUseCase markAsCompletedUseCase;
    private final GetWaitingListUseCase getWaitingListUseCase;

    public PreparationController(
            CreatePreparationUseCase createUseCase,
            StartNextPreparationUseCase startNextUseCase,
            MarkPreparationAsReadyUseCase markAsReadyUseCase,
            MarkPreparationAsCompletedUseCase markAsCompletedUseCase,
            GetWaitingListUseCase getWaitingListUseCase
    ) {
        this.createUseCase = createUseCase;
        this.startNextUseCase = startNextUseCase;
        this.markAsReadyUseCase = markAsReadyUseCase;
        this.markAsCompletedUseCase = markAsCompletedUseCase;
        this.getWaitingListUseCase = getWaitingListUseCase;
    }

    public Preparation create(CreatePreparationCommand command) {
        return this.createUseCase.execute(command);
    }

    public Preparation startNext() {
        return this.startNextUseCase.execute();
    }

    public Preparation markAsReady(MarkPreparationAsReadyCommand command) {
        return this.markAsReadyUseCase.execute(command);
    }

    public Preparation markAsCompleted(MarkPreparationAsCompletedCommand command) {
        return this.markAsCompletedUseCase.execute(command);
    }

    public List<Preparation> getWaitingList() {
        return this.getWaitingListUseCase.execute();
    }
}
