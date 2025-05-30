package com.store.soattechchallenge.category.infrastructure.adapters.out.repository;

import com.store.soattechchallenge.category.infrastructure.adapters.in.dto.CategoryProductProjectionDTO;
import com.store.soattechchallenge.category.infrastructure.adapters.out.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryAdaptersRepository extends JpaRepository<CategoryEntity,Long> {
    @Query(value = "SELECT c.id AS categoriaId, c.nm_categoria AS nomeCategoria, " +
            "p.id AS produtoId, p.nm_produto AS nomeProduto, " +
            "p.vl_unitario_produto, p.tempo_de_preparo_produto, p.dt_inclusao " +
            "FROM tb_categoria_itens c " +
            "LEFT JOIN tb_produto p ON p.id_categoria = c.id " +
            "WHERE c.id = :id", nativeQuery = true)
    List<Object[]> findCategoryWithProducts(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tb_produto WHERE id_categoria = :categoryId", nativeQuery = true)
    void deleteProductsByCategoryId(@Param("categoryId") Long categoryId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tb_categoria_itens WHERE id = :categoryId", nativeQuery = true)
    void deleteCategoryById(@Param("categoryId") Long categoryId);

    CategoryEntity findByCategoryName(String categoryName);
}
