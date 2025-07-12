package com.store.soattechchallenge.shoppingCart.product.infrastructure.configuration;

import com.store.soattechchallenge.shoppingCart.product.application.usecases.CreateProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.application.usecases.DeleteProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.application.usecases.FindProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.application.usecases.UpdateProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.controller.ProductMainController;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.gateways.ProductRepositoryGatewayGateways;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.jpa.ProductAdaptersGetRepository;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.mappers.ProductMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {
    @Bean
    ProductMainController productMainController(FindProductUseCase findProductUseCase, CreateProductUseCase createProductUseCase, UpdateProductUseCase updateProductUseCase, DeleteProductUseCase deleteProductUseCase) {
        return new ProductMainController(findProductUseCase, createProductUseCase, updateProductUseCase, deleteProductUseCase);
    }
    @Bean
    FindProductUseCase findProductUseCase(ProductRepositoryGatewayGateways adaptersRepository) {
        return new FindProductUseCase(adaptersRepository);
    }
    @Bean
    CreateProductUseCase createProductUseCase(ProductRepositoryGatewayGateways adaptersRepository) {
        return new CreateProductUseCase(adaptersRepository);
    }
    @Bean
    UpdateProductUseCase updateProductUseCase(ProductRepositoryGatewayGateways adaptersRepository) {
        return new UpdateProductUseCase(adaptersRepository);
    }
    @Bean
    DeleteProductUseCase deleteProductUseCase(ProductRepositoryGatewayGateways adaptersRepository) {
        return new DeleteProductUseCase(adaptersRepository);
    }
    @Bean
    ProductRepositoryGatewayGateways productRepositoryGatewayGateways(ProductAdaptersGetRepository repository, ProductMapper mapper) {
        return new ProductRepositoryGatewayGateways(repository, mapper);
    }

}
