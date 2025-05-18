package com.store.soattechchallenge.category.domain.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.Objects;

public class Category {

    private String categoryName;
    private Date dateInclusion;
    private Timestamp timestamp;

    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.dateInclusion = new Date(System.currentTimeMillis());
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }

    public Category(){

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

    public Timestamp getTimestamp() {
        return timestamp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryName, category.categoryName)
                && Objects.equals(dateInclusion, category.dateInclusion) && Objects.equals(timestamp, category.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash( categoryName, dateInclusion, timestamp);
    }

    @Override
    public String toString() {
        return "Category{" +
                ", categoryName='" + categoryName + '\'' +
                ", dateInclusion=" + dateInclusion +
                ", timestamp=" + timestamp +
                '}';
    }

}