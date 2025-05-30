package com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.in.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class CategoryProductProjectionDTO {
    private Long categoriaId;
    private String nomeCategoria;
    private Long produtoId;
    private String nomeProduto;
    private BigDecimal vlUnitarioProduto;
    private Integer tempoDePreparo;
    private Date dtInclusao;

    public CategoryProductProjectionDTO(Long categoriaId, String nomeCategoria, Long produtoId, String nomeProduto,
                                        BigDecimal vlUnitarioProduto, Integer tempoDePreparo, Date dtInclusao) {
        this.categoriaId = categoriaId;
        this.nomeCategoria = nomeCategoria;
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.vlUnitarioProduto = vlUnitarioProduto;
        this.tempoDePreparo = tempoDePreparo;
        this.dtInclusao = dtInclusao;
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
    public Long getProdutoId() {
        return produtoId;
    }
    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public BigDecimal getVlUnitarioProduto() {
        return vlUnitarioProduto;
    }
    public void setVlUnitarioProduto(BigDecimal vlUnitarioProduto) {
        this.vlUnitarioProduto = vlUnitarioProduto;
    }
    public Integer getTempoDePreparo() {
        return tempoDePreparo;
    }
    public void setTempoDePreparo(Integer tempoDePreparo) {
        this.tempoDePreparo = tempoDePreparo;
    }
    public Date getDtInclusao() {
        return dtInclusao;
    }
    public void setDtInclusao(Date dtInclusao) {
        this.dtInclusao = dtInclusao;
    }
}