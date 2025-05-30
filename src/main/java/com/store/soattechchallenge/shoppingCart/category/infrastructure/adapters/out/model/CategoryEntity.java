package com.store.soattechchallenge.shoppingCart.category.infrastructure.adapters.out.model;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_categoria_itens")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_categoria")
    private String categoryName;

    @Column(name = "dt_inclusao")
    private Date dateInclusion;

    private Timestamp timestamp;

    public CategoryEntity() {
    }

    public CategoryEntity(Long id, String categoryName, Date dateInclusion, Timestamp timestamp) {
        this.id = id;
        this.categoryName = categoryName;
        this.dateInclusion = dateInclusion;
        this.timestamp = timestamp;
    }

    public static CategoryEntity builder() {
        return new CategoryEntity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getDateInclusion() {
        return dateInclusion;
    }

    public void setDateInclusion(Date dateInclusion) {
        this.dateInclusion = dateInclusion;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryEntity that = (CategoryEntity) o;

        if (!id.equals(that.id)) return false;
        if (!categoryName.equals(that.categoryName)) return false;
        if (!dateInclusion.equals(that.dateInclusion)) return false;
        return timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + categoryName.hashCode();
        result = 31 * result + dateInclusion.hashCode();
        result = 31 * result + timestamp.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", dateInclusion=" + dateInclusion +
                ", timestamp=" + timestamp +
                '}';
    }
}