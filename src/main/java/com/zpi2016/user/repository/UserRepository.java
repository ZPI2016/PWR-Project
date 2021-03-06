package com.zpi2016.user.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.zpi2016.location.domain.Location;
import com.zpi2016.user.domain.User;

/**
 * Created by kuba on 28.02.16.
 */
public interface UserRepository extends CrudRepository <User, UUID> {

    public User findByUsername(String username);

    public User findByEmail(String email);

    public User findByUsernameOrEmail(String username, String email);

    public List<User> findByFirstName(String firstName);

    public List<User> findByLastName(String lastName);

    public List<User> findByFirstNameAndLastName(String firstName, String lastName);

    public List<User> findByFirstNameOrLastName(String firstName, String lastName);

    public List<User> findByDob(Date dob);

    public List<User> findByDobAfter(Date date);

    public List<User> findByDobBefore(Date date);

    public List<User> findByDobBetween(Date start, Date end);

    public List<User> findByAddress(Location address);
}
