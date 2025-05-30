package com.store.soattechchallenge.preparation.infrastructure.adapters.out.repository;

import com.store.soattechchallenge.preparation.infrastructure.adapters.out.model.JpaPreparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PreparationJpaRepository extends JpaRepository<JpaPreparation, String> {
    Optional<JpaPreparation> findFirstByPreparationStatusOrderByPreparationPositionDesc(String stPreparacao);
    Optional<JpaPreparation> findFirstByPreparationStatusOrderByPreparationPositionAsc(String stPreparacao);
    List<JpaPreparation> findByPreparationStatusOrderByPreparationPositionAsc(String stPreparacao);
    List<JpaPreparation> findByPreparationStatusOrderByEstimatedReadyTimeAsc(String stPreparacao);
    List<JpaPreparation> findByPreparationStatusOrderByTimestampAsc(String stPreparacao);
    @Modifying
    @Query("""
        UPDATE JpaPreparation p
        SET p.preparationPosition = p.preparationPosition - 1
        WHERE p.preparationStatus = 'RECEIVED'
        AND p.preparationPosition > :preparationPosition
    """)
    void decrementReceivedPositionsGreaterThan(@Param("preparationPosition") Integer preparationPosition);
}
