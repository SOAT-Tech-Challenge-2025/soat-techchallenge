package com.store.soattechchallenge.preparation.domain.repository;

import com.store.soattechchallenge.preparation.domain.model.Preparation;

import java.util.List;
import java.util.Optional;

public interface PreparationRepository {
    public Preparation save(Preparation preparation);
    public Preparation findById(String id);
    public Boolean existsById(String id);
    public Integer findMaxPosition();
    public Optional<Preparation> findReceivedWithMinPosition();
    public void decrementReceivedPositionsGreaterThan(Integer preparationPosition);
    public List<Preparation> getReceivedWaitingList();
    public List<Preparation> getInPreparationWaitingList();
    public List<Preparation> getReadyWaitingList();
}