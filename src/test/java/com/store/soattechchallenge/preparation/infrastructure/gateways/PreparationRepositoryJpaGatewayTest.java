package com.store.soattechchallenge.preparation.infrastructure.gateways;

import com.store.soattechchallenge.preparation.gateways.exceptions.EntityNotFoundException;
import com.store.soattechchallenge.preparation.domain.PreparationStatus;
import com.store.soattechchallenge.preparation.domain.entites.Preparation;
import com.store.soattechchallenge.preparation.infrastructure.jpa.JpaPreparation;
import com.store.soattechchallenge.preparation.infrastructure.jpa.PreparationJpaRepository;
import com.store.soattechchallenge.preparation.infrastructure.mappers.PreparationMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PreparationRepositoryJpaGatewayTest {
    @InjectMocks
    private PreparationRepositoryJpaGateway preparationRepositoryJpaGateway;

    @Mock
    private PreparationJpaRepository preparationJpaRepository;

    @Mock
    private PreparationMapper preparationMapper;

    @Test
    void shouldReturnPreparationWhenSaveIsCalled() {
        Preparation preparation = new Preparation(
                "PREPARATION-TEST-01",
                null,
                2,
                null,
                PreparationStatus.READY,
                LocalDateTime.parse("2025-07-01T00:00:00"),
                LocalDateTime.parse("2025-07-01T00:00:00")
        );

        JpaPreparation jpaPreparation = new JpaPreparation(preparation);
        BDDMockito
                .given(this.preparationMapper.fromDomainToJpa(preparation))
                .willReturn(jpaPreparation);

        BDDMockito
                .given(this.preparationJpaRepository.save(jpaPreparation))
                .willReturn(jpaPreparation);

        BDDMockito
                .given(this.preparationMapper.fromJpaToDomain(jpaPreparation))
                .willReturn(preparation);

        Preparation savedPreparation = this.preparationRepositoryJpaGateway.save(preparation);
        BDDMockito.verify(preparationJpaRepository).save(jpaPreparation);
        BDDMockito.verify(this.preparationMapper).fromDomainToJpa(preparation);
        BDDMockito.verify(this.preparationMapper).fromJpaToDomain(jpaPreparation);
        this.comparePreparations(preparation, savedPreparation);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenPreparationNotFoundById() {
        String preparationId = "PREPARATION-TEST-01";
        BDDMockito
                .given(this.preparationJpaRepository.findById(preparationId))
                .willReturn(Optional.empty());

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> this.preparationRepositoryJpaGateway.findById(preparationId),
                "Preparation with ID " + preparationId + " not found"
        );

        BDDMockito.verify(this.preparationJpaRepository).findById(preparationId);
    }

    @Test
    void shouldReturnPreparationWhenFoundById() {
        Preparation preparation = new Preparation(
                "PREPARATION-TEST-01",
                null,
                2,
                null,
                PreparationStatus.READY,
                LocalDateTime.parse("2025-07-01T00:00:00"),
                LocalDateTime.parse("2025-07-01T00:00:00")
        );

        JpaPreparation jpaPreparation = new JpaPreparation(preparation);
        BDDMockito
                .given(this.preparationJpaRepository.findById(preparation.getId()))
                .willReturn(Optional.of(jpaPreparation));

        BDDMockito
                .given(this.preparationMapper.fromJpaToDomain(jpaPreparation))
                .willReturn(preparation);

        Preparation preparationFound = this.preparationRepositoryJpaGateway.findById(preparation.getId());
        BDDMockito.verify(this.preparationJpaRepository).findById(preparation.getId());
        BDDMockito.verify(this.preparationMapper).fromJpaToDomain(jpaPreparation);
        this.comparePreparations(preparation, preparationFound);
    }

    @Test
    void shouldReturnTrueWhenPreparationExistsById() {
        String preparationId = "PREPARATION-TEST-01";
        BDDMockito
                .given(this.preparationJpaRepository.existsById(preparationId))
                .willReturn(true);

        Boolean preparationExists = this.preparationRepositoryJpaGateway.existsById(preparationId);
        BDDMockito.verify(this.preparationJpaRepository).existsById(preparationId);
        Assertions.assertEquals(true, preparationExists);
    }

    @Test
    void shouldReturnFalseWhenPreparationDoesNotExistById() {
        String preparationId = "PREPARATION-TEST-01";
        BDDMockito
                .given(this.preparationJpaRepository.existsById(preparationId))
                .willReturn(false);

        Boolean preparationExists = this.preparationRepositoryJpaGateway.existsById(preparationId);
        BDDMockito.verify(this.preparationJpaRepository).existsById(preparationId);
        Assertions.assertEquals(false, preparationExists);
    }

    @Test
    void shouldReturnZeroWhenNoPreparationWithStatusReceivedExists() {
        BDDMockito
                .given(
                        this.preparationJpaRepository.findFirstByPreparationStatusOrderByPreparationPositionDesc(
                                PreparationStatus.RECEIVED.toString()
                        )
                )
                .willReturn(Optional.empty());

        Integer maxPosition = this.preparationRepositoryJpaGateway.findMaxPosition();
        Assertions.assertEquals(0, maxPosition);
        BDDMockito.verify(this.preparationJpaRepository).findFirstByPreparationStatusOrderByPreparationPositionDesc(
                PreparationStatus.RECEIVED.toString()
        );
    }

    @Test
    void shouldReturnPositionOfLatestPreparationWithStatusReceived() {
        Preparation latestReceivedPreparation = new Preparation(
                "PREPARATION-TEST-01",
                1,
                2,
                null,
                PreparationStatus.RECEIVED,
                LocalDateTime.parse("2025-07-01T00:00:00"),
                LocalDateTime.parse("2025-07-01T00:00:00")
        );

        JpaPreparation latestReceivedJpaPreparation = new JpaPreparation(latestReceivedPreparation);
        BDDMockito
                .given(
                        this.preparationJpaRepository.findFirstByPreparationStatusOrderByPreparationPositionDesc(
                                PreparationStatus.RECEIVED.toString()
                        )
                )
                .willReturn(Optional.of(latestReceivedJpaPreparation));

        Integer maxPosition = this.preparationRepositoryJpaGateway.findMaxPosition();
        Assertions.assertEquals(1, maxPosition);
        BDDMockito.verify(this.preparationJpaRepository).findFirstByPreparationStatusOrderByPreparationPositionDesc(
                PreparationStatus.RECEIVED.toString()
        );
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoPreparationWithStatusReceivedExists() {
        BDDMockito
                .given(
                        this.preparationJpaRepository.findFirstByPreparationStatusOrderByPreparationPositionAsc(
                                PreparationStatus.RECEIVED.toString()
                        )
                )
                .willReturn(Optional.empty());

        Optional<Preparation> preparation = this.preparationRepositoryJpaGateway.findReceivedWithMinPosition();
        Assertions.assertTrue(preparation.isEmpty());
        BDDMockito
                .verify(this.preparationJpaRepository)
                .findFirstByPreparationStatusOrderByPreparationPositionAsc(PreparationStatus.RECEIVED.toString());
    }

    @Test
    void shouldReturnPreparationWithLowestPositionAndStatusReceived() {
        Preparation preparation = new Preparation(
                "PREPARATION-TEST-01",
                1,
                2,
                null,
                PreparationStatus.RECEIVED,
                LocalDateTime.parse("2025-07-01T00:00:00"),
                LocalDateTime.parse("2025-07-01T00:00:00")
        );

        JpaPreparation jpaPreparation = new JpaPreparation(preparation);
        BDDMockito
                .given(
                        this.preparationJpaRepository.findFirstByPreparationStatusOrderByPreparationPositionAsc(
                                PreparationStatus.RECEIVED.toString()
                        )
                )
                .willReturn(Optional.of(jpaPreparation));

        BDDMockito
                .given(this.preparationMapper.fromJpaToDomain(jpaPreparation))
                .willReturn(preparation);

        Optional<Preparation> optionalPreparation = this.preparationRepositoryJpaGateway.findReceivedWithMinPosition();
        Assertions.assertTrue(optionalPreparation.isPresent());
        this.comparePreparations(preparation, optionalPreparation.get());
        BDDMockito
                .verify(this.preparationJpaRepository)
                .findFirstByPreparationStatusOrderByPreparationPositionAsc(PreparationStatus.RECEIVED.toString());

        BDDMockito.verify(this.preparationMapper).fromJpaToDomain(jpaPreparation);
    }

    @Test
    void shouldDecrementPositionsOfReceivedPreparationsGreaterThanGivenValue() {
        this.preparationRepositoryJpaGateway.decrementReceivedPositionsGreaterThan(1);
        BDDMockito.verify(this.preparationJpaRepository).decrementReceivedPositionsGreaterThan(1);
    }

    @Test
    void shouldReturnListOfReceivedPreparationsOrderedByPosition() {
        Preparation preparation1 = new Preparation(
                "PREPARATION-TEST-01",
                1,
                2,
                null,
                PreparationStatus.RECEIVED,
                LocalDateTime.parse("2025-07-01T00:00:00"),
                LocalDateTime.parse("2025-07-01T00:00:00")
        );

        Preparation preparation2 = new Preparation(
                "PREPARATION-TEST-02",
                2,
                1,
                null,
                PreparationStatus.RECEIVED,
                LocalDateTime.parse("2025-07-01T01:00:00"),
                LocalDateTime.parse("2025-07-01T01:00:00")
        );

        JpaPreparation jpaPreparation1 = new JpaPreparation(preparation1);
        JpaPreparation jpaPreparation2 = new JpaPreparation(preparation2);
        List<JpaPreparation> jpaPreparationList = List.of(jpaPreparation1, jpaPreparation2);
        BDDMockito
                .given(
                    this.preparationJpaRepository.findByPreparationStatusOrderByPreparationPositionAsc(
                            PreparationStatus.RECEIVED.toString()
                    )
                )
                .willReturn(jpaPreparationList);

        BDDMockito
                .given(this.preparationMapper.fromJpaToDomain(jpaPreparation1))
                .willReturn(preparation1);

        BDDMockito
                .given(this.preparationMapper.fromJpaToDomain(jpaPreparation2))
                .willReturn(preparation2);

        List<Preparation> preparationsFound = this.preparationRepositoryJpaGateway.getReceivedWaitingList();
        this.comparePreparations(preparation1, preparationsFound.get(0));
        this.comparePreparations(preparation2, preparationsFound.get(1));
        BDDMockito.verify(this.preparationJpaRepository).findByPreparationStatusOrderByPreparationPositionAsc(
                PreparationStatus.RECEIVED.toString()
        );

        BDDMockito.verify(this.preparationMapper).fromJpaToDomain(jpaPreparation1);
        BDDMockito.verify(this.preparationMapper).fromJpaToDomain(jpaPreparation2);
    }

    @Test
    void shouldReturnListOfInPreparationPreparationsOrderedByEstimatedReadyTime() {
        Preparation preparation1 = new Preparation(
                "PREPARATION-TEST-04",
                null,
                1,
                LocalDateTime.parse("2025-07-01T00:03:00"),
                PreparationStatus.IN_PREPARATION,
                LocalDateTime.parse("2025-07-01T00:01:00"),
                LocalDateTime.parse("2025-07-01T00:02:00")
        );

        Preparation preparation2 = new Preparation(
                "PREPARATION-TEST-03",
                null,
                4,
                LocalDateTime.parse("2025-07-01T00:05:00"),
                PreparationStatus.IN_PREPARATION,
                LocalDateTime.parse("2025-07-01T00:00:00"),
                LocalDateTime.parse("2025-07-01T00:01:00")
        );

        JpaPreparation jpaPreparation1 = new JpaPreparation(preparation1);
        JpaPreparation jpaPreparation2 = new JpaPreparation(preparation2);
        List<JpaPreparation> jpaPreparationList = List.of(jpaPreparation1, jpaPreparation2);
        BDDMockito
                .given(
                        this.preparationJpaRepository.findByPreparationStatusOrderByEstimatedReadyTimeAsc(
                                PreparationStatus.IN_PREPARATION.toString()
                        )
                )
                .willReturn(jpaPreparationList);

        BDDMockito
                .given(this.preparationMapper.fromJpaToDomain(jpaPreparation1))
                .willReturn(preparation1);

        BDDMockito
                .given(this.preparationMapper.fromJpaToDomain(jpaPreparation2))
                .willReturn(preparation2);

        List<Preparation> preparationsFound = this.preparationRepositoryJpaGateway.getInPreparationWaitingList();
        this.comparePreparations(preparation1, preparationsFound.get(0));
        this.comparePreparations(preparation2, preparationsFound.get(1));
        BDDMockito.verify(this.preparationJpaRepository).findByPreparationStatusOrderByEstimatedReadyTimeAsc(
                PreparationStatus.IN_PREPARATION.toString()
        );

        BDDMockito.verify(this.preparationMapper).fromJpaToDomain(jpaPreparation1);
        BDDMockito.verify(this.preparationMapper).fromJpaToDomain(jpaPreparation2);
    }

    @Test
    void shouldReturnListOfReadyPreparationsOrderedByTimestamp() {
        Preparation preparation1 = new Preparation(
                "PREPARATION-TEST-05",
                null,
                1,
                null,
                PreparationStatus.READY,
                LocalDateTime.parse("2025-07-01T00:01:00"),
                LocalDateTime.parse("2025-07-01T00:02:00")
        );

        Preparation preparation2 = new Preparation(
                "PREPARATION-TEST-06",
                null,
                4,
                null,
                PreparationStatus.READY,
                LocalDateTime.parse("2025-07-01T00:00:00"),
                LocalDateTime.parse("2025-07-01T00:03:00")
        );

        JpaPreparation jpaPreparation1 = new JpaPreparation(preparation1);
        JpaPreparation jpaPreparation2 = new JpaPreparation(preparation2);
        List<JpaPreparation> jpaPreparationList = List.of(jpaPreparation1, jpaPreparation2);
        BDDMockito
                .given(
                        this.preparationJpaRepository.findByPreparationStatusOrderByTimestampAsc(
                                PreparationStatus.READY.toString()
                        )
                )
                .willReturn(jpaPreparationList);

        BDDMockito
                .given(this.preparationMapper.fromJpaToDomain(jpaPreparation1))
                .willReturn(preparation1);

        BDDMockito
                .given(this.preparationMapper.fromJpaToDomain(jpaPreparation2))
                .willReturn(preparation2);

        List<Preparation> preparationsFound = this.preparationRepositoryJpaGateway.getReadyWaitingList();
        this.comparePreparations(preparation1, preparationsFound.get(0));
        this.comparePreparations(preparation2, preparationsFound.get(1));
        BDDMockito.verify(this.preparationJpaRepository).findByPreparationStatusOrderByTimestampAsc(
                PreparationStatus.READY.toString()
        );

        BDDMockito.verify(this.preparationMapper).fromJpaToDomain(jpaPreparation1);
        BDDMockito.verify(this.preparationMapper).fromJpaToDomain(jpaPreparation2);
    }

    private void comparePreparations (Preparation preparation1, Preparation preparation2) {
        Assertions.assertEquals(preparation1.getId(), preparation2.getId());
        Assertions.assertEquals(preparation1.getPreparationPosition(), preparation2.getPreparationPosition());
        Assertions.assertEquals(preparation1.getPreparationTime(), preparation2.getPreparationTime());
        Assertions.assertEquals(preparation1.getEstimatedReadyTime(), preparation2.getEstimatedReadyTime());
        Assertions.assertEquals(preparation1.getCreatedAt(), preparation2.getCreatedAt());
        Assertions.assertEquals(preparation1.getTimestamp(), preparation2.getTimestamp());
    }
}
