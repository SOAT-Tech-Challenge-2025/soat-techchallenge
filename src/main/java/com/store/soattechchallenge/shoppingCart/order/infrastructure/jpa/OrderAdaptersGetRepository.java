package com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderAdaptersGetRepository extends JpaRepository<JPAOrderEntity,String> {
    @Query(value = "SELECT generate_order_id()", nativeQuery = true)
    String getByOrderId();

    @Query("SELECT o FROM JPAOrderEntity o JOIN FETCH o.produtos WHERE o.id = :id")
    Optional<JPAOrderEntity> findByIdWithProdutos(@Param("id") String id);
}
