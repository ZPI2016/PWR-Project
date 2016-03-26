package com.zpi2016.rating.domain;

import com.zpi2016.support.common.GenericEntity;
import com.zpi2016.user.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by filip on 26.02.2016.
 */
@MappedSuperclass
public class Rating extends GenericEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private User author;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Rate overall;

    @Column(length = 500, nullable = false)
    private String descr;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

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
