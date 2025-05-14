package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.repository;

import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.model.JPAPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagamentoJPARepository extends JpaRepository<JPAPagamento, String> {
    public Optional<JPAPagamento> findByIdExterno(String idExterno);
}
