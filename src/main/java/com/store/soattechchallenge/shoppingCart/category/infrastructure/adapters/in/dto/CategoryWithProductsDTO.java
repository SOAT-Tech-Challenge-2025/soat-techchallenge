package com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto;

import java.util.List;

public class CategoryWithProductsDTO {
    private Long categoriaId;
    private String nomeCategoria;
    private List<ProductDTO> produtos;

    public CategoryWithProductsDTO() {}

    public CategoryWithProductsDTO(Long categoriaId, String nomeCategoria, List<ProductDTO> produtos) {
        this.categoriaId = categoriaId;
        this.nomeCategoria = nomeCategoria;
        this.produtos = produtos;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public List<ProductDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProductDTO> produtos) {
        this.produtos = produtos;
    }
}