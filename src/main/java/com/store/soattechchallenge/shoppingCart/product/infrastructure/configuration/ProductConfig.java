package com.store.soattechchallenge.shoppingCart.product.infrastructure.configuration;


import com.store.soattechchallenge.shoppingCart.product.usecases.CreateProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.usecases.DeleteProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.usecases.FindProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.usecases.UpdateProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.controller.ProductMainController;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.gateways.ProductRepositoryGatewayGateways;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.jpa.ProductAdaptersGetRepository;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.mappers.ProductMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {
    @Bean
    ProductMainController productMainController(ProductRepositoryGatewayGateways adaptersRepository, ProductMapper productMapper) {
        return new ProductMainController(adaptersRepository,productMapper);
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
