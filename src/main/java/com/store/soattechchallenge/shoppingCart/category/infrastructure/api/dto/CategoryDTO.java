package com.store.soattechchallenge.shoppingCart.category.infrastructure.api.dto;

import com.store.soattechchallenge.shoppingCart.category.infrastructure.jpa.JpaCategory;

import java.sql.Date;
import java.sql.Timestamp;

public class CategoryDTO {
    private Long id;
    private String categoryName;
    private Date dateInclusion;
    private Timestamp timestamp;
    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String categoryName, Date dateInclusion, Timestamp timestamp) {
        this.id = id;
        this.categoryName = categoryName;
        this.dateInclusion = dateInclusion;
        this.timestamp = timestamp;
    }

    public static JpaCategory builder() {
        return new JpaCategory();
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