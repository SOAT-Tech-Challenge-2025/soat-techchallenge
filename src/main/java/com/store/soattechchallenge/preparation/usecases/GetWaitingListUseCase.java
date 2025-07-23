package com.store.soattechchallenge.preparation.usecases;

import com.store.soattechchallenge.preparation.gateways.PreparationRepositoryGateway;
import com.store.soattechchallenge.preparation.domain.entites.Preparation;

import java.util.List;

public class GetWaitingListUseCase {
    private final PreparationRepositoryGateway preparationRepositoryGateway;

    public GetWaitingListUseCase(PreparationRepositoryGateway preparationRepositoryGateway) {
        this.preparationRepositoryGateway = preparationRepositoryGateway;
    }

    public List<Preparation> execute() {
        List<Preparation> preparations = this.preparationRepositoryGateway.getReadyWaitingList();
        preparations.addAll(this.preparationRepositoryGateway.getInPreparationWaitingList());
        preparations.addAll(this.preparationRepositoryGateway.getReceivedWaitingList());
        return preparations;
    }
}
