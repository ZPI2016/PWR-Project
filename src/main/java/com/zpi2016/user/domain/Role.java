package com.zpi2016.user.domain;

import com.zpi2016.support.common.GenericEntity;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by aman on 10.04.16.
 */
@Entity
@Table(name = "roles")
public class Role extends GenericEntity{

    public Role(){}

    public Role(String role){
        this.role = role;
    }

    public Role(User user, String role){
        this.userId = user.getId();
        this.role = role;
    }

    @Column(name = "userid")
    private UUID userId;

    @Column(name = "role")
    private String role;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
