package com.zpi2016.model;

import javax.persistence.*;

/**
 * Created by filip on 27.02.2016.
 */

@Entity
@Table(name = "EVENTS_RATINGS")
public class EventRating extends Rating {
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private User author;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID", nullable = false)
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
