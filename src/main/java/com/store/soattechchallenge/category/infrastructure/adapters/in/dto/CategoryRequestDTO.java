package com.store.soattechchallenge.category.infrastructure.adapters.in.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class CategoryRequestDTO {
    private Long id;
    private String categoryName;
    private Date dateInclusion;
    private Timestamp timestamp;


}