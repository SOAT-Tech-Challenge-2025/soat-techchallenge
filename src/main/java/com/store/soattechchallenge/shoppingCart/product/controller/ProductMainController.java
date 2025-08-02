package com.store.soattechchallenge.shoppingCart.product.controller;

import com.store.soattechchallenge.shoppingCart.product.domain.entities.Product;
import com.store.soattechchallenge.shoppingCart.product.gateways.ProductRepositoryGateway;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductPostUpResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.mappers.ProductMapper;
import com.store.soattechchallenge.shoppingCart.product.presenters.ProductHttpPresenter;
import com.store.soattechchallenge.shoppingCart.product.usecases.CreateProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.usecases.DeleteProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.usecases.FindProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.usecases.UpdateProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.usecases.command.ProductRequestCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class ProductMainController {

    public final ProductRepositoryGateway productRepositoryGateway;
    private final ProductMapper productMapper;

    public ProductMainController(ProductRepositoryGateway productRepositoryGateway, ProductMapper productMapper) {
        this.productRepositoryGateway = productRepositoryGateway;
        this.productMapper = productMapper;

    }

    public ProductPostUpResponseDTO saveProduct(ProductRequestCommand command) {
        CreateProductUseCase createProductUseCase = new  CreateProductUseCase(this.productRepositoryGateway);
        ProductHttpPresenter productHttpPresenter = new ProductHttpPresenter(this.productMapper);
        createProductUseCase.saveProduct(command);
        return productHttpPresenter.execute();
    }

    public Page<ProductGetResponseDTO> getAllProducts(Pageable pageable) {
        FindProductUseCase findProductUseCase = new FindProductUseCase(this.productRepositoryGateway);
        ProductHttpPresenter productHttpPresenter = new ProductHttpPresenter(this.productMapper);
        Page<Product> jpaProducts = findProductUseCase.getAllProducts(pageable);
        return productHttpPresenter.executeGetAllProducts(jpaProducts);
    }

    public Optional<ProductGetResponseDTO> getProductById(Long id) {
        FindProductUseCase findProductUseCase = new FindProductUseCase(this.productRepositoryGateway);
        ProductHttpPresenter productHttpPresenter = new ProductHttpPresenter(this.productMapper);
        Optional<Product> jpaProduct = findProductUseCase.getProductById(id);
        return productHttpPresenter.executeGetProductById(jpaProduct);
    }

    public ProductPostUpResponseDTO updateProduct(Long id, ProductRequestCommand command) {
        UpdateProductUseCase updateProductUseCase = new UpdateProductUseCase(this.productRepositoryGateway);
        ProductHttpPresenter productHttpPresenter = new ProductHttpPresenter(this.productMapper);
        Boolean update = updateProductUseCase.updateProduct(id,command);
        return productHttpPresenter.executeUpdate(update);
    }

    public ProductPostUpResponseDTO deleteProduct(Long id) {
        DeleteProductUseCase deleteProductUseCase = new DeleteProductUseCase(this.productRepositoryGateway);
        ProductHttpPresenter productHttpPresenter = new ProductHttpPresenter(this.productMapper);
        Boolean delete = deleteProductUseCase.deleteProduct(id);
        return productHttpPresenter.executeDelete(delete);
    }


}
