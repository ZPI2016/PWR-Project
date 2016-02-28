package com.zpi2016.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

    @Column(name = "USERNAME", length = 50, nullable = false)
    private String username;

    @Column(name = "PASSWORD", length = 50, nullable = false)
    private String password;

    @Column(name = "EMAIL", length = 80, nullable = false)
    private String email;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    private final Set<Event> createdEvents = new HashSet<Event>(0);

    @ManyToMany(fetch = FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL, mappedBy = "participants")
    private final Set<Event> attendedEvents = new HashSet<Event>(0);

    @Column(name = "FIRSTNAME", length = 50)
    private String firstName;

    @Column(name = "LASTNAME", length = 50)
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name = "DOB", nullable = false)
    private Date DOB;

    @OneToOne
    @JoinColumn(name="LOCATION_ID", nullable = false)
    private Location address;

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

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
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

    public Set<Event> getAttendedEvents() {
        return attendedEvents;
    }

    public Set<Event> getCreatedEvents() {
        return createdEvents;
    }
}
