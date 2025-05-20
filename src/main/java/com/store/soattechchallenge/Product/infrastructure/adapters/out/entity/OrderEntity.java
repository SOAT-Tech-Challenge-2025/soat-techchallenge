package com.store.soattechchallenge.Product.infrastructure.adapters.out.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_carrinho_pedido")
public class OrderEntity {
    @Id
    private UUID id;

    @Column(name = "vl_total_pedido")
    private BigDecimal orderTotal;

    @Column(name = "minutagem")
    private Integer minuteCount;

    @Column(name = "id_cliente")
    private UUID clientId;

    @Column(name = "timestamp")
    private Timestamp timestamp;

}