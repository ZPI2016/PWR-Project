package com.zpi2016.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by filip on 27.02.2016.
 */

@Entity
public class UserRating extends Rating {

	@ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private User user;
	
	public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
	
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
