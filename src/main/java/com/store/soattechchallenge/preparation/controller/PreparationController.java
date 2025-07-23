package com.store.soattechchallenge.preparation.controller;

import com.store.soattechchallenge.preparation.gateways.PreparationRepositoryGateway;
import com.store.soattechchallenge.preparation.infrastructure.api.dto.ItemsResponseDTO;
import com.store.soattechchallenge.preparation.infrastructure.api.dto.PreparationResponseDTO;
import com.store.soattechchallenge.preparation.infrastructure.gateways.PreparationRepositoryJpaGateway;
import com.store.soattechchallenge.preparation.infrastructure.jpa.PreparationJpaRepository;
import com.store.soattechchallenge.preparation.infrastructure.mappers.PreparationMapper;
import com.store.soattechchallenge.preparation.presenters.PreparationHttpPresenter;
import com.store.soattechchallenge.preparation.presenters.WaitingListHttpPresenter;
import com.store.soattechchallenge.preparation.usecases.CreatePreparationUseCase;
import com.store.soattechchallenge.preparation.usecases.MarkPreparationAsReadyUseCase;
import com.store.soattechchallenge.preparation.usecases.MarkPreparationAsCompletedUseCase;
import com.store.soattechchallenge.preparation.usecases.StartNextPreparationUseCase;
import com.store.soattechchallenge.preparation.usecases.GetWaitingListUseCase;
import com.store.soattechchallenge.preparation.usecases.commands.CreatePreparationCommand;
import com.store.soattechchallenge.preparation.usecases.commands.MarkPreparationAsCompletedCommand;
import com.store.soattechchallenge.preparation.usecases.commands.MarkPreparationAsReadyCommand;
import com.store.soattechchallenge.preparation.domain.entites.Preparation;

import java.util.List;

public class PreparationController {
    private final PreparationRepositoryGateway preparationRepositoryGateway;
    private final PreparationMapper preparationMapper;

    public PreparationController(
            PreparationJpaRepository preparationJpaRepository,
            PreparationMapper preparationMapper
    ) {
        this.preparationMapper = preparationMapper;
        this.preparationRepositoryGateway = new PreparationRepositoryJpaGateway(
                preparationJpaRepository, this.preparationMapper
        );
    }

    public PreparationResponseDTO create(CreatePreparationCommand command) {
        CreatePreparationUseCase useCase = new CreatePreparationUseCase(this.preparationRepositoryGateway);
        PreparationHttpPresenter presenter = new PreparationHttpPresenter(this.preparationMapper);
        Preparation preparation = useCase.execute(command);
        return presenter.present(preparation);
    }

    public PreparationResponseDTO startNext() {
        StartNextPreparationUseCase useCase = new StartNextPreparationUseCase(this.preparationRepositoryGateway);
        PreparationHttpPresenter presenter = new PreparationHttpPresenter(this.preparationMapper);
        Preparation preparation = useCase.execute();
        return presenter.present(preparation);
    }

    public PreparationResponseDTO markAsReady(MarkPreparationAsReadyCommand command) {
        MarkPreparationAsReadyUseCase useCase = new MarkPreparationAsReadyUseCase(this.preparationRepositoryGateway);
        PreparationHttpPresenter presenter = new PreparationHttpPresenter(this.preparationMapper);
        Preparation preparation = useCase.execute(command);
        return presenter.present(preparation);
    }

    public PreparationResponseDTO markAsCompleted(MarkPreparationAsCompletedCommand command) {
        MarkPreparationAsCompletedUseCase useCase = new MarkPreparationAsCompletedUseCase(
                this.preparationRepositoryGateway
        );

        PreparationHttpPresenter presenter = new PreparationHttpPresenter(this.preparationMapper);
        Preparation preparation = useCase.execute(command);
        return presenter.present(preparation);
    }

    public ItemsResponseDTO<PreparationResponseDTO> getWaitingList() {
        GetWaitingListUseCase useCase = new GetWaitingListUseCase(this.preparationRepositoryGateway);
        WaitingListHttpPresenter presenter = new WaitingListHttpPresenter(this.preparationMapper);
        List<Preparation> preparations = useCase.execute();
        return presenter.present(preparations);
    }
}
