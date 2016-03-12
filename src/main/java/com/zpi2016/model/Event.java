package com.zpi2016.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by filip on 26.02.2016.
 */
@Entity
@Table(name = "EVENTS")
public class Event extends GenericEntity<Event> {

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY", nullable = false)
    private Category category;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STARTTIME", nullable = false)
    private Date startTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="LOCATION_ID", nullable = false)
    private Location place;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREATOR_ID", nullable = false)
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EVENT_USER", joinColumns = { @JoinColumn(name = "EVENT_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
    private final Set<User> participants = new HashSet<User>(0);

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private final Set<EventRating> ratings = new HashSet<EventRating>(0);
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DEADLINE")
    private Date deadline;

    @Column(name = "MINPARTICIPANTS")
    private Integer minParticipants;

    @Column(name = "MAXPARTICIPANTS")
    private Integer maxParticipants;

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
}
