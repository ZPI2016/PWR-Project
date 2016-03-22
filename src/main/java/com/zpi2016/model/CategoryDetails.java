package com.zpi2016.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by filip on 26.02.2016.
 */
@Entity
public class CategoryDetails extends GenericEntity<CategoryDetails> {

    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category;

    @Column(length = 500, nullable = false)
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
