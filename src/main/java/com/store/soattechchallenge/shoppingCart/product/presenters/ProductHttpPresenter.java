package com.store.soattechchallenge.shoppingCart.product.presenters;

import com.store.soattechchallenge.shoppingCart.order.infrastructure.api.dto.OrderResponseDTO;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.jpa.JPAOrderEntity;
import com.store.soattechchallenge.shoppingCart.order.infrastructure.mappers.OrderMapper;
import com.store.soattechchallenge.shoppingCart.product.domain.entities.Product;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductGetResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.api.dto.ProductPostUpResponseDTO;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.jpa.JpaProduct;
import com.store.soattechchallenge.shoppingCart.product.infrastructure.mappers.ProductMapper;
import org.springframework.data.domain.Page;

import java.util.Optional;

public class ProductHttpPresenter {
    private final ProductMapper productMapper;

    public ProductHttpPresenter(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public ProductPostUpResponseDTO execute(){
        return new ProductPostUpResponseDTO("Produto salvo com sucesso");
    }

    public ProductPostUpResponseDTO executeUpdate(Boolean update){
        if (update) {
            return new ProductPostUpResponseDTO("Produto atualizado com sucesso");
        } else {
            return new ProductPostUpResponseDTO("Produto não atualizado");
        }
    }

    public ProductPostUpResponseDTO executeDelete(Boolean update){
        if (update) {
            return new ProductPostUpResponseDTO("Produto deletado com sucesso");
        } else {
            return new ProductPostUpResponseDTO("Produto não existe");
        }
    }

    public Page<ProductGetResponseDTO> executeGetAllProducts(Page<Product> products){
        return productMapper.modelToProductGetResponsePageDTO(products);
    }

    public Optional<ProductGetResponseDTO> executeGetProductById(Optional<Product> product) {
        return productMapper.modelToProductGetResponseOptionalDTO(product);
    }

}
