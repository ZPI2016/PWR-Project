package com.zpi2016.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by filip on 27.02.2016.
 */

@Entity
public class EventRating extends Rating {
	
	@ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private User author;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Event event;
	
	public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
	
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
