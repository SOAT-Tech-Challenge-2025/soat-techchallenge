package com.store.soattechchallenge.preparation.domain.entities;

import com.store.soattechchallenge.preparation.domain.entites.Preparation;
import com.store.soattechchallenge.preparation.domain.PreparationStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class PreparationTest {
    @Test void shouldUpdateInPreparationPreparationToReady () {
        Preparation preparation = new Preparation(
                "PREPARATION-TEST-01",
                null,
                2,
                LocalDateTime.now().plusMinutes(2),
                PreparationStatus.IN_PREPARATION,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        preparation.ready();
        Assertions.assertEquals(PreparationStatus.READY, preparation.getPreparationStatus());
    }

    @Test
    public void shouldNotUpdateNotInPreparationPreparationToReady () {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Preparation preparation = new Preparation(
                    "PREPARATION-TEST-02",
                    1,
                    2,
                    null,
                    PreparationStatus.RECEIVED,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            preparation.ready();
        }, "A preparation with RECEIVED status cannot be updated to READY");
    }

    @Test
    void shouldUpdateReadyPreparationToCompleted () {
        Preparation preparation = new Preparation(
                "PREPARATION-TEST-03",
                null,
                2,
                null,
                PreparationStatus.READY,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        preparation.complete();
        Assertions.assertEquals(PreparationStatus.COMPLETED, preparation.getPreparationStatus());
    }

    @Test
    public void shouldNotUpdateNotReadyPreparationToCompleted () {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Preparation preparation = new Preparation(
                    "PREPARATION-TEST-04",
                    null,
                    2,
                    LocalDateTime.now().plusMinutes(2),
                    PreparationStatus.IN_PREPARATION,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            preparation.complete();
        }, "A preparation with IN_PREPARATION status cannot be updated to COMPLETED");
    }
}
