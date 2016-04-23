package com.zpi2016.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zpi2016.event.domain.Event;
import com.zpi2016.location.domain.Location;
import com.zpi2016.rating.domain.EventRating;
import com.zpi2016.rating.domain.UserRating;
import com.zpi2016.support.common.GenericEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by filip on 26.02.2016.
 */
@Entity
@Table(name = "Users")
public class User extends GenericEntity {

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 80, nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private final Set<Event> createdEvents = new HashSet<Event>(0);

    @JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<UserRating> ratings = new HashSet<UserRating>(0);

    @JsonIgnore
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private final Set<UserRating> givenUserRatings = new HashSet<UserRating>(0);

    @JsonIgnore
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private final Set<EventRating> givenEventRatings = new HashSet<EventRating>(0);

    @JsonIgnore
    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private final Set<Event> attendedEvents = new HashSet<Event>(0);

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dob;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private Location address;

    @NotNull
	private Float radius = 10.0f;

    public User() {}

    private User(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dob = builder.dob;
        this.address = builder.address;
        this.radius = builder.radius;
    }

    public void copy(User other) {
        if (other.username != null && !this.username.equals(other.username))    this.username = other.username;
        if (other.password != null && !this.password.equals(other.password))    this.password = other.password;
        if (other.email != null && !this.email.equals(other.email))             this.email = other.email;
        if (other.firstName != null && !this.firstName.equals(other.firstName)) this.firstName = other.firstName;
        if (other.lastName != null && !this.lastName.equals(other.lastName))    this.lastName = other.lastName;
        if (other.dob != null  && !this.dob.equals(other.dob))                  this.dob = other.dob;
        if (other.radius != null && !this.radius.equals(other.radius))          this.radius = other.radius;
        if (other.address != null) this.address.copy(other.address);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Location getAddress() {
        return address;
    }

    public void setAddress(Location address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
	
	public Float getRadius() {
		return radius;
	}
	
	public void setRadius(Float radius) {
		this.radius = radius;
	}
	
    public Set<Event> getAttendedEvents() {
        return attendedEvents;
    }

    public Set<Event> getCreatedEvents() {
        return createdEvents;
    }

    public Set<UserRating> getRatings() {
        return ratings;
    }

    public Set<UserRating> getGivenUserRatings() {
        return givenUserRatings;
    }

    public Set<EventRating> getGivenEventRatings() {
        return givenEventRatings;
    }

    public static class Builder {

        private final String username;
        private final String password;
        private final String email;
        private String firstName;
        private String lastName;
        private final Date dob;
        private final Location address;
        private final Float radius;

        public Builder(String username, String password, String email, Date dob, Location address, Float radius) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.dob = dob;
            this.address = address;
            this.radius = radius;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
