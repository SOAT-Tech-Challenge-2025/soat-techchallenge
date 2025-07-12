package com.store.soattechchallenge.shoppingCart.product.controller;

import com.store.soattechchallenge.shoppingCart.product.application.usecases.CreateProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.application.usecases.DeleteProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.application.usecases.FindProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.application.usecases.UpdateProductUseCase;
import com.store.soattechchallenge.shoppingCart.product.application.usecases.command.ProductRequestCommand;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductPostUpResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductMainController {

    public final FindProductUseCase findProductUseCase;
    public final CreateProductUseCase createProductUseCase;
    public final UpdateProductUseCase updateProductUseCase;
    public final DeleteProductUseCase deleteProductUseCase;

    public ProductMainController(FindProductUseCase findProductUseCase, CreateProductUseCase createProductUseCase, UpdateProductUseCase updateProductUseCase, DeleteProductUseCase deleteProductUseCase) {
        this.findProductUseCase = findProductUseCase;
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }

    public ProductPostUpResponseDTO saveProduct(ProductRequestCommand command) {
        return createProductUseCase.saveProduct(command);
    }

    public Page<ProductGetResponseDTO> getAllProducts(Pageable pageable) {
        return findProductUseCase.getAllProducts(pageable);
    }

    public Optional<ProductGetResponseDTO> getProductById(Long id) {
        return findProductUseCase.getProductById(id);
    }

    public ProductPostUpResponseDTO updateProduct(Long id, ProductRequestCommand command) {
        return  updateProductUseCase.updateProduct(id,command);
    }

    public ProductPostUpResponseDTO deleteProduct(Long id) {
        return deleteProductUseCase.deleteProduct(id);
    }


}
