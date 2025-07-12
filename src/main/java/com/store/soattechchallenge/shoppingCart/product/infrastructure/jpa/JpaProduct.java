package com.store.soattechchallenge.shoppingCart.product.infrastructure.jpa;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tb_produto")
public class JpaProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_produto")
    private String productName;

    @Column(name = "id_categoria")
    private Long idCategory;

    @Column(name = "vl_unitario_produto")
    private BigDecimal unitPrice;

    @Column(name = "tempo_de_preparo_produto")
    private Integer preparationTime;

    @Column(name = "dt_inclusao")
    private Date inclusionDate;

    @Column(name = "\"timestamp\"")
    private Timestamp timestamp;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(final Long idCategory) {
        this.idCategory = idCategory;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(final BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(final Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Date getInclusionDate() {
        return inclusionDate;
    }

    public void setInclusionDate(final Date inclusionDate) {
        this.inclusionDate = inclusionDate;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final JpaProduct jpaProduct = (JpaProduct) o;
        return Objects.equals(id, jpaProduct.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", idCategory=" + idCategory +
                ", unitPrice=" + unitPrice +
                ", preparationTime=" + preparationTime +
                ", inclusionDate=" + inclusionDate +
                ", timestamp=" + timestamp +
                '}';
    }

}