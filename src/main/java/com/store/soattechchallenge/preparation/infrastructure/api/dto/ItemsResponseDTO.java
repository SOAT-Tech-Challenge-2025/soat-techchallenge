package com.store.soattechchallenge.preparation.infrastructure.api.dto;

import java.util.List;

public record ItemsResponseDTO<T>(List<T> items) {}
