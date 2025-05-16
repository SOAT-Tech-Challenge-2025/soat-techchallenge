package com.store.soattechchallenge.category.infrastructure.adapters.out.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tb_categoria_itens")

public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_categoria")
    private String categoryName;

    @Column(name = "dt_inclusao")
    private Date dateInclusion;

    private Timestamp timestamp;

}