package com.zpi2016.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by filip on 26.02.2016.
 */
@Entity
@Table(name = "USERS")
public class User extends GenericEntity<User> {

    @Column(name = "USERNAME", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", length = 50, nullable = false)
    private String password;

    @Column(name = "EMAIL", length = 80, nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private final Set<Event> createdEvents = new HashSet<Event>(0);

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<UserRating> ratings = new HashSet<UserRating>(0);
	
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private final Set<UserRating> givenUserRatings = new HashSet<UserRating>(0);

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private final Set<EventRating> givenEventRatings = new HashSet<EventRating>(0);
	
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "participants")
    private final Set<Event> attendedEvents = new HashSet<Event>(0);

    @Column(name = "FIRSTNAME", length = 50)
    private String firstName;

    @Column(name = "LASTNAME", length = 50)
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name = "DOB", nullable = false)
    private Date dob;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="LOCATION_ID", nullable = false)
    private Location address;
	
	@Column(name = "RADIUS", nullable = false)
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
