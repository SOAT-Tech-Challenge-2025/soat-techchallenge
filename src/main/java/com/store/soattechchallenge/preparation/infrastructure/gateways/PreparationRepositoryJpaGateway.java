package com.store.soattechchallenge.preparation.infrastructure.gateways;

import com.store.soattechchallenge.preparation.domain.entites.Preparation;
import com.store.soattechchallenge.preparation.domain.PreparationStatus;
import com.store.soattechchallenge.preparation.gateways.PreparationRepositoryGateway;
import com.store.soattechchallenge.preparation.gateways.exceptions.EntityNotFoundException;
import com.store.soattechchallenge.preparation.infrastructure.jpa.JpaPreparation;
import com.store.soattechchallenge.preparation.infrastructure.jpa.PreparationJpaRepository;
import com.store.soattechchallenge.preparation.infrastructure.mappers.PreparationMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PreparationRepositoryJpaGateway implements PreparationRepositoryGateway {
    private final PreparationJpaRepository preparationJpaRepository;
    private final PreparationMapper preparationMapper;

    public PreparationRepositoryJpaGateway(
            PreparationJpaRepository preparationJpaRepository,
            PreparationMapper preparationMapper
    ) {
        this.preparationJpaRepository = preparationJpaRepository;
        this.preparationMapper = preparationMapper;
    }

    @Override
    public Preparation save(Preparation preparation) {
        JpaPreparation jpaPreparation = this.preparationJpaRepository.save(this.preparationMapper.fromDomainToJpa(preparation));
        return this.preparationMapper.fromJpaToDomain(jpaPreparation);
    }

    @Override
    public Preparation findById(String id) {
        Optional<JpaPreparation> optionalJpaPreparation = this.preparationJpaRepository.findById(id);
        if (optionalJpaPreparation.isEmpty()) {
            throw new EntityNotFoundException("Preparation with ID " + id + " not found");
        }

        return this.preparationMapper.fromJpaToDomain(optionalJpaPreparation.get());
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

        return optionalJpaPreparation.map(this.preparationMapper::fromJpaToDomain);

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
                .map(this.preparationMapper::fromJpaToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Preparation> getInPreparationWaitingList() {
        return this.preparationJpaRepository.findByPreparationStatusOrderByEstimatedReadyTimeAsc(
                        PreparationStatus.IN_PREPARATION.toString()
                )
                .stream()
                .map(this.preparationMapper::fromJpaToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Preparation> getReadyWaitingList() {
        return this.preparationJpaRepository.findByPreparationStatusOrderByTimestampAsc(
                        PreparationStatus.READY.toString()
                )
                .stream()
                .map(this.preparationMapper::fromJpaToDomain)
                .collect(Collectors.toList());
    }
}
