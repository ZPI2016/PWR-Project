package com.zpi2016.model;

import javax.persistence.*;

/**
 * Created by filip on 27.02.2016.
 */

@Entity
@Table(name = "USERS_RATINGS")
public class UserRating extends Rating {
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private User author;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
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
