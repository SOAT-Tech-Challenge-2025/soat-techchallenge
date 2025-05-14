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

public class CategoryResponseDTO {
    private String categoryName;
}