package com.zpi2016.rating.domain;

import com.zpi2016.event.domain.Event;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by filip on 27.02.2016.
 */

@Entity
public class EventRating extends Rating {

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Event event;
	
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
