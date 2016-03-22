package com.zpi2016.repository;

import com.zpi2016.model.Location;
import com.zpi2016.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by kuba on 28.02.16.
 */
public interface UserRepository extends CrudRepository <User, String> {

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
