package com.zpi2016.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 * Created by filip on 26.02.2016.
 */
@MappedSuperclass
public class Rating extends GenericEntity<Rating> {

    @Enumerated(EnumType.STRING)
    @Column(name = "OVERALL", nullable = false)
    private Rate overall;

    @Column(name = "DESCR", length = 500, nullable = false)
    private String descr;

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Rate getOverall() {
        return overall;
    }

    public void setOverall(Rate overall) {
        this.overall = overall;
    }
}
