package com.zpi2016.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Created by filip on 26.02.2016.
 */
@MappedSuperclass
public class Rating extends GenericEntity<Rating> {

    @Enumerated(EnumType.STRING)
    @NotNull
    private Rate overall;

    @Column(length = 500, nullable = false)
    private String descr;

    public Rate getOverall() {
        return overall;
    }

    public void setOverall(Rate overall) {
        this.overall = overall;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
