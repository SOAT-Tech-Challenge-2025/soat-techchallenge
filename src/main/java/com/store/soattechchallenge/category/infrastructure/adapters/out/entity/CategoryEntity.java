package com.store.soattechchallenge.category.infrastructure.adapters.out.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class CategoryEntity {

    private Long id;
    private String categoryName;
    private Date dateInclusion;
    private Timestamp timestamp;

    public CategoryEntity(Long id, String categoryName, Date dateInclusion, Timestamp timestamp) {
        this.id = id;
        this.categoryName = categoryName;
        this.dateInclusion = dateInclusion;
        this.timestamp = timestamp;
    }

    public CategoryEntity(){

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
        CategoryEntity category = (CategoryEntity) o;
        return Objects.equals(id, category.id) && Objects.equals(categoryName, category.categoryName)
                && Objects.equals(dateInclusion, category.dateInclusion) && Objects.equals(timestamp, category.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName, dateInclusion, timestamp);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", dateInclusion=" + dateInclusion +
                ", timestamp=" + timestamp +
                '}';
    }
}