package com.store.soattechchallenge.preparation.infrastructure.adapters.out.repository.impl;

import com.store.soattechchallenge.preparation.domain.model.Preparation;
import com.store.soattechchallenge.preparation.domain.model.PreparationStatus;
import com.store.soattechchallenge.preparation.domain.repository.PreparationRepository;
import com.store.soattechchallenge.preparation.infrastructure.adapters.out.model.JpaPreparation;
import com.store.soattechchallenge.preparation.infrastructure.adapters.out.mapper.PreparationMapper;
import com.store.soattechchallenge.preparation.infrastructure.adapters.out.repository.PreparationJpaRepository;
import com.store.soattechchallenge.preparation.infrastructure.adapters.out.repository.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PreparationRepositoryImpl implements PreparationRepository {
    PreparationJpaRepository preparationJpaRepository;
    PreparationMapper preparationMapper;

    public PreparationRepositoryImpl(
            PreparationJpaRepository preparationJpaRepository,
            PreparationMapper preparationMapper
    ) {
        this.preparationJpaRepository = preparationJpaRepository;
        this.preparationMapper = preparationMapper;
    }

    @Override
    public Preparation save(Preparation preparation) {
        JpaPreparation jpaPreparation = this.preparationJpaRepository.save(this.preparationMapper.toJPA(preparation));
        return this.preparationMapper.toDomain(jpaPreparation);
    }

    @Override
    public Preparation findById(String id) {
        Optional<JpaPreparation> optionalJpaPreparation = this.preparationJpaRepository.findById(id);
        if (optionalJpaPreparation.isEmpty()) {
            throw new EntityNotFoundException("Preparation with ID " + id + " not found");
        }

        return this.preparationMapper.toDomain(optionalJpaPreparation.get());
    }

    @Override
    public Boolean existsById(String id) {
        return this.preparationJpaRepository.existsById(id);
    }

    @Override
    public Integer findMaxPosition() {
        Optional<JpaPreparation>optionalJpaPreparation = this.preparationJpaRepository
                .findFirstByPreparationStatusOrderByPreparationPositionDesc(PreparationStatus.RECEIVED.toString());

        Integer maxPosicao = 0;
        if (optionalJpaPreparation.isPresent()) {
            maxPosicao = optionalJpaPreparation.get().getPreparationPosition();
        }

        return maxPosicao != null ? maxPosicao : 0;
    }

    @Override
    public Optional<Preparation> findReceivedWithMinPosition() {
        Optional<JpaPreparation>optionalJpaPreparation = this.preparationJpaRepository
                .findFirstByPreparationStatusOrderByPreparationPositionAsc(PreparationStatus.RECEIVED.toString());

        return optionalJpaPreparation.map(jpaPreparation -> this.preparationMapper.toDomain(jpaPreparation));

    }

    @Override
    public void decrementReceivedPositionsGreaterThan(Integer preparationPosition) {
        this.preparationJpaRepository.decrementReceivedPositionsGreaterThan(preparationPosition);
    }

    @Override
    public List<Preparation> getReceivedWaitingList() {
        return this.preparationJpaRepository
                .findByPreparationStatusOrderByPreparationPositionAsc(PreparationStatus.RECEIVED.toString())
                .stream()
                .map(this.preparationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Preparation> getInPreparationWaitingList() {
        return this.preparationJpaRepository.findByPreparationStatusOrderByEstimatedReadyTimeAsc(
                    PreparationStatus.IN_PREPARATION.toString()
                )
                .stream()
                .map(this.preparationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Preparation> getReadyWaitingList() {
        return this.preparationJpaRepository.findByPreparationStatusOrderByTimestampAsc(
                        PreparationStatus.READY.toString()
                )
                .stream()
                .map(this.preparationMapper::toDomain)
                .collect(Collectors.toList());
    }
}
