package com.zpi2016.rating.domain;

import com.zpi2016.user.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
