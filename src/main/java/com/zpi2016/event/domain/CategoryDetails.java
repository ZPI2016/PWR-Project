package com.zpi2016.event.domain;

import com.zpi2016.support.common.GenericEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by filip on 26.02.2016.
 */
@Entity
public class CategoryDetails extends GenericEntity {

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
