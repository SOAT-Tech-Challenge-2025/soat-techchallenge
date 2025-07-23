package com.store.soattechchallenge.preparation.infrastructure.configuration;

import com.store.soattechchallenge.preparation.infrastructure.mappers.PreparationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PreparationConfig {
    @Bean
    PreparationMapper preparationMapper() {
        return new PreparationMapper();
    }
}
