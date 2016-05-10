package com.zpi2016.rating.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.zpi2016.user.domain.User;

/**
 * Created by filip on 27.02.2016.
 */

@Entity
public class UserRating extends Rating {

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private User user;
	
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
