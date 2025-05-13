package com.store.soattechchallenge.pagamento.infrastructure.adapters.out.repository;

import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.model.JPAPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoJPARepository extends JpaRepository<JPAPagamento, String> {
}
