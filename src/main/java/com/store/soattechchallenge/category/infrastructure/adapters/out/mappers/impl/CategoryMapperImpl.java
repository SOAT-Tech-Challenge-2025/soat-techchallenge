package com.store.soattechchallenge.category.infrastructure.adapters.out.mappers.impl;

import com.store.soattechchallenge.category.domain.model.Category;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryProductProjectionDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryWithProductsDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.ProductDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import com.store.soattechchallenge.category.infrastructure.adapters.out.mappers.CategoryMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryMapperImpl implements CategoryMapper{

    @Override
    public CategoryEntity toCategoryEntityMap(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(category.getCategoryName());
        categoryEntity.setDateInclusion(category.getDateInclusion());
        categoryEntity.setTimestamp(category.getTimestamp());
        return categoryEntity;
    }

    @Override
    public CategoryEntity toCategoryUpdateMap(Category category,Long id){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(id);
        categoryEntity.setCategoryName(category.getCategoryName());
        categoryEntity.setDateInclusion(category.getDateInclusion());
        categoryEntity.setTimestamp(category.getTimestamp());
        return categoryEntity;
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
                        .map(p -> new ProductDTO(p.getProdutoId(), p.getNomeProduto(),p.getVlUnitarioProduto(),p.getTempoDePreparo(),p.getDtInclusao()))
                        .toList()
        );

        return Optional.of(dto);
    }

}
