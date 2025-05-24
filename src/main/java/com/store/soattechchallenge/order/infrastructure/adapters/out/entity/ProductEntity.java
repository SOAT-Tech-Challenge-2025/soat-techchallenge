package com.store.soattechchallenge.order.infrastructure.adapters.out.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_produto")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_produto")
    private String productName;

    @Column(name = "id_categoria")
    private Long idCategory;

    @Column(name = "vl_unitario_produto")
    private BigDecimal unitPrice;

    @Column(name = "tempo_de_preparo")
    private Integer preparationTimeMinutes;

    @Column(name = "dt_inclusao")
    private LocalDate inclusionDate;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

}