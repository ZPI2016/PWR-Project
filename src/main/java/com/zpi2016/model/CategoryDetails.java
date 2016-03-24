package com.zpi2016.model;

import javax.persistence.*;

/**
 * Created by filip on 26.02.2016.
 */
@Entity
@Table(name = "CATEGORIES_DETAILS")
public class CategoryDetails extends GenericEntity<CategoryDetails> {

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY", nullable = false)
    private Category category;

    @Column(name = "DESCR", length = 500, nullable = false)
    private String descr;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}