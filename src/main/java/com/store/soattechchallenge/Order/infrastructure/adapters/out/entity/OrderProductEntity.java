package com.store.soattechchallenge.Order.infrastructure.adapters.out.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_carrinho_produto")
public class OrderProductEntity {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id")
    private Product product;

    @Column(name = "qt_item")
    private Integer quantityItem;

    @Column(name = "vl_qt_item")
    private BigDecimal itemValue;
}
