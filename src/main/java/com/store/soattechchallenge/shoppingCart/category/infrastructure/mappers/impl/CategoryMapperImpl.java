package com.store.soattechchallenge.shoppingCart.category.infrastructure.mappers.impl;

import com.store.soattechchallenge.shoppingCart.category.domain.entities.Category;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryProductProjectionDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto.ProductDTO;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.jpa.JpaCategory;
import com.store.soattechchallenge.shoppingCart.category.infrastructure.mappers.CategoryMapper;
import org.springframework.data.domain.Page;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class CategoryMapperImpl implements CategoryMapper{

    @Override
    public JpaCategory toCategoryEntityMap(Category category) {
        JpaCategory jpaCategory = new JpaCategory();
        jpaCategory.setCategoryName(category.getCategoryName());
        jpaCategory.setDateInclusion(category.getDateInclusion());
        jpaCategory.setTimestamp(category.getTimestamp());
        return jpaCategory;
    }

    @Override
    public JpaCategory toCategoryUpdateMap(Category category, Long id){
        JpaCategory jpaCategory = new JpaCategory();
        jpaCategory.setId(id);
        jpaCategory.setCategoryName(category.getCategoryName());
        jpaCategory.setDateInclusion(category.getDateInclusion());
        jpaCategory.setTimestamp(category.getTimestamp());
        return jpaCategory;
    }

    @Override
    public Page<CategoryDTO> toCategoryDTO(Page<JpaCategory> jpaCategory) {
        return jpaCategory.map(this::toCategoryDTO);
    }

    @Override
    public CategoryDTO toCategoryDTO(JpaCategory jpaCategory) {
        if (jpaCategory == null) {
            return null;
        }
        return new CategoryDTO(
                jpaCategory.getId(),
                jpaCategory.getCategoryName(),
                jpaCategory.getDateInclusion(),
                jpaCategory.getTimestamp()
        );
    }
    @Override
    public Optional<CategoryWithProductsDTO> toProductCategoryEntity(List<CategoryProductProjectionDTO> projectionDTOList) {
        if (projectionDTOList == null || projectionDTOList.isEmpty()) {
            return Optional.empty();
        }
        CategoryProductProjectionDTO first = projectionDTOList.get(0);
        CategoryWithProductsDTO dto = new CategoryWithProductsDTO();
        dto.setCategoriaId(first.getCategoriaId());
        dto.setNomeCategoria(first.getNomeCategoria());
        dto.setProdutos(
                projectionDTOList.stream()
                        .filter(p -> p.getProdutoId() != null)
                        .sorted(Comparator.comparing(CategoryProductProjectionDTO::getProdutoId))
                        .map(p -> new ProductDTO(
                                p.getProdutoId(),
                                p.getNomeProduto(),
                                p.getVlUnitarioProduto(),
                                p.getTempoDePreparo(),
                                p.getDtInclusao()))
                        .toList()
        );
        return Optional.of(dto);
    }

}
