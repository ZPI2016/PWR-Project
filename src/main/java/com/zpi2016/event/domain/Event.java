package com.zpi2016.event.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zpi2016.location.domain.Location;
import com.zpi2016.rating.domain.EventRating;
import com.zpi2016.support.common.GenericEntity;
import com.zpi2016.user.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by filip on 26.02.2016.
 */
@Entity
public class Event extends GenericEntity {

    @NotNull
    private String title;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date startTime;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private Location place;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private User creator;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private final Set<User> participants = new HashSet<User>(0);

    @JsonIgnore
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private final Set<EventRating> ratings = new HashSet<EventRating>(0);

    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;

    private Integer minParticipants;

    private Integer maxParticipants;

    public Event() {
    }

    private Event(Builder builder) {
        this.title = builder.title;
        this.category = builder.category;
        this.startTime = builder.startTime;
        this.place = builder.place;
        this.creator = builder.creator;
        this.deadline = builder.deadline;
        this.maxParticipants = builder.maxParticipants;
        this.minParticipants = builder.minParticipants;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Location getPlace() {
        return place;
    }

    public void setPlace(Location place) {
        this.place = place;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(Integer minParticipants) {
        this.minParticipants = minParticipants;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<EventRating> getRatings() {
        return ratings;
    }

    public void copy(Event other) {

        if (other.title != null && !this.title.equals(other.title)) this.title = other.title;
        if (other.category != null && !this.category.equals(other.category)) this.category = other.category;
        if (other.startTime != null && !this.startTime.equals(other.startTime)) this.startTime = other.startTime;
        if (other.place != null && !this.place.equals(other.place)) this.place = other.place;
        if (other.creator != null && !this.creator.equals(other.creator)) this.creator = other.creator;
        if (other.deadline != null && !this.deadline.equals(other.deadline)) this.deadline = other.deadline;
        if (other.minParticipants != null && !this.minParticipants.equals(other.minParticipants))
            this.minParticipants = other.minParticipants;
        if (other.maxParticipants != null && !this.maxParticipants.equals(other.maxParticipants))
            this.maxParticipants = other.maxParticipants;

    }

    public static class Builder {

        private final Category category;
        private final Date startTime;
        private final Location place;
        private final User creator;
        private Date deadline;
        private Integer minParticipants;
        private Integer maxParticipants;
        public String title;

        public Builder(String title, Category category, Date startTime, Location place, User creator) {
            this.title = title;
            this.category = category;
            this.startTime = startTime;
            this.place = place;
            this.creator = creator;
        }

        public Builder withDeadline(Date deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder withMinParticipants(Integer minParticipants) {
            this.minParticipants = minParticipants;
            return this;
        }

        public Builder withMaxParticipants(Integer maxParticipants) {
            this.maxParticipants = maxParticipants;
            return this;
        }

        public Event build() {
            return new Event(this);
        }

    }
}
