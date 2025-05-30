
package com.store.soattechchallenge.preparation.application.usecases;

import com.store.soattechchallenge.preparation.domain.model.Preparation;

import java.util.List;

public interface PreparationUseCase {
    public Preparation create(String id, Integer preparationTime);
    public Preparation startNext();
    public Preparation ready(String id);
    public Preparation finalize(String id);
    public List<Preparation> waitingList();
}