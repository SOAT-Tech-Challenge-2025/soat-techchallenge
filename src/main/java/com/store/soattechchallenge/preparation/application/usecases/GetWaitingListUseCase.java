package com.store.soattechchallenge.preparation.application.usecases;

import com.store.soattechchallenge.preparation.application.gateways.PreparationGateway;
import com.store.soattechchallenge.preparation.domain.entites.Preparation;

import java.util.List;

public class GetWaitingListUseCase {
    private final PreparationGateway preparationGateway;

    public GetWaitingListUseCase(PreparationGateway preparationGateway) {
        this.preparationGateway = preparationGateway;
    }

    public List<Preparation> execute() {
        List<Preparation> preparations = this.preparationGateway.getReadyWaitingList();
        preparations.addAll(this.preparationGateway.getInPreparationWaitingList());
        preparations.addAll(this.preparationGateway.getReceivedWaitingList());
        return preparations;
    }
}
