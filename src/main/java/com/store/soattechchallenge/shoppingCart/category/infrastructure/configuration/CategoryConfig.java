package com.store.soattechchallenge.shoppingCart.category.infrastructure.configuration;

import com.store.soattechchallenge.shoppingCart.category.application.usecases.*;
import com.store.soattechchallenge.shoppingCart.category.controller.CategoryAppController;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.gateways.CategoryGatewayGateway;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.jpa.CategoryAdaptersRepository;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.mappers.CategoryMapper;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.mappers.impl.CategoryMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfig {


    @Bean
    CategoryGatewayGateway categoryRepositoryGateway(CategoryAdaptersRepository repository, CategoryMapper mapper) {
        return new CategoryGatewayGateway(repository, mapper);
    }

    @Bean
    CategoryMapperImpl categoryMapperImpl() {
        return new CategoryMapperImpl();
    }

    @Bean
    CategoryAppController categoryAppController(
            CreateCategoryUseCase createCategoryUseCase,
            FindCategoryUseCase findCategoryUseCase,
            UpdateCategoryUseCase updateCategoryUseCase,
            DeleteCategoryUseCase deleteCategoryUseCase,
            FindProductsByCategoryUseCase findProductsByCategoryUseCase
    ) {
        return new CategoryAppController(
                createCategoryUseCase,
                findCategoryUseCase,
                updateCategoryUseCase,
                deleteCategoryUseCase,
                findProductsByCategoryUseCase
        );
    }

    @Bean
    CreateCategoryUseCase createCategoryUseCase(
            CategoryGatewayGateway categoryRepositoryGateway
    ) {
        return new CreateCategoryUseCase(categoryRepositoryGateway);
    }

    @Bean
    FindCategoryUseCase findCategoryUseCase(
            CategoryGatewayGateway categoryRepositoryGateway
    ) {
        return new FindCategoryUseCase(categoryRepositoryGateway);
    }

    @Bean
    UpdateCategoryUseCase updateCategoryUseCase(
            CategoryGatewayGateway categoryRepositoryGateway
    ) {
        return new UpdateCategoryUseCase(categoryRepositoryGateway);
    }

    @Bean
    DeleteCategoryUseCase deleteCategoryUseCase(
            CategoryGatewayGateway categoryRepositoryGateway
    ) {
        return new DeleteCategoryUseCase(categoryRepositoryGateway);
    }

    @Bean
    FindProductsByCategoryUseCase findProductsByCategoryUseCase(
            CategoryGatewayGateway categoryRepositoryGateway
    ) {
        return new FindProductsByCategoryUseCase(categoryRepositoryGateway);
    }
}
