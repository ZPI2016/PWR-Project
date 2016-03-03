
package com.zpi2016.repository;

import com.zpi2016.Application;
import com.zpi2016.model.Location;
import com.zpi2016.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class UserRepositoryTest {

    private UserRepository userRepository;
    private LocationRepository locationRepository;
    private User user;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Before
    public void init(){
        // setup the user
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Nowak");
        user.setUsername("jurny_jan");
        user.setPassword("haslo");
        user.setEmail("jan.nowak@dummy.com");
        Date dob = new Date();
        dob.setTime(1005260400000l);
        user.setDOB(dob);

        // setup the location
        Location wroclaw = new Location();
        wroclaw.setGeoLatitude(51.107885f);
        wroclaw.setGeoLongitude(17.038538f);
        locationRepository.save(wroclaw);

        user.setAddress(wroclaw);
    }


    @After
    public void clean(){
        Location userLocation = locationRepository.findOne(user.getAddress().getId());
        // ZAWSZE USUWAJCIE W TEN SPOSÓB!!! Jeśli ppodamy obiekt, to mamy wyścig i dostaniemy błąd
        userRepository.delete(user.getId());
        locationRepository.delete(userLocation);
    }

    /**
     * Basic test of saving functionality of UserRepository
     */
    @Test
    public void saveUserTest() {

        // the id should be null before saving the object to db
        Assert.assertNull(user.getId());

        userRepository.save(user);

        // the id should be now initialized
        Assert.assertNotNull(user.getId());

        // fetching product from db
        User fetchedUser = userRepository.findOne(user.getId());

        // checking if fetching was succesfull
        Assert.assertNotNull(fetchedUser);

        // checking the equality
        Assert.assertEquals(user.getId(), fetchedUser.getId());

        // checking the updating functionality
        fetchedUser.setEmail("jan.nowak@gmail.com");
        userRepository.save(fetchedUser);
        User fetchedUpdatedUser = userRepository.findOne(fetchedUser.getId());
        Assert.assertEquals(user.getId(), fetchedUpdatedUser.getId());
        Assert.assertNotEquals(user.getEmail(), fetchedUpdatedUser.getEmail());

        // checking if the entries are not duplicated
        Iterable<User> fetchedUsers = userRepository.findAll();
        int usersCount = 0;
        for (User u : fetchedUsers)
            usersCount++;
        Assert.assertEquals(1, usersCount);
    }

    /**
     * Test of different queries in user's repo
     */
    @Test
    public void variousUserQueriesTest(){
        userRepository.save(user);

        User userByUsername = userRepository.findByUsername("jurny_jan");
        Assert.assertEquals(user.getId(), userByUsername.getId());

        User userByEmail = userRepository.findByEmail("jan.nowak@dummy.com");
        Assert.assertEquals(user.getId(), userByEmail.getId());

        List<User> usersByFirstName = userRepository.findByFirstName("Jan");
        Assert.assertEquals(1, usersByFirstName.size());
        Assert.assertEquals(user.getId(), usersByFirstName.get(0).getId());

        List<User> usersByLastName = userRepository.findByLastName("Nowak");
        Assert.assertEquals(1, usersByLastName.size());
        Assert.assertEquals(user.getId(), usersByLastName.get(0).getId());

        List<User> usersByFirstAndLast = userRepository.findByFirstNameAndLastName("Jan", "Nowak");
        Assert.assertEquals(1, usersByFirstAndLast.size());
        Assert.assertEquals(user.getId(), usersByFirstAndLast.get(0).getId());
        List<User> usersByFirstAndLast2 = userRepository.findByFirstNameAndLastName("Janek", "Nowak");
        Assert.assertEquals(0, usersByFirstAndLast2.size());

        List<User> usersByFirstOrLast = userRepository.findByFirstNameOrLastName("Janek", "Nowak");
        Assert.assertEquals(1, usersByFirstOrLast.size());
        Assert.assertEquals(user.getId(), usersByFirstOrLast.get(0).getId());

        Date dob = new Date(1005260400000l);
        List<User> usersByDOB = userRepository.findByDob(dob);
        Assert.assertEquals(1, usersByDOB.size());
        Assert.assertEquals(user.getId(), usersByDOB.get(0).getId());
        Date after = new Date(1267569430000l);
        Date before = new Date(952036630000l);
        List<User> usersBeforeInvalid = userRepository.findByDobBefore(before);
        Assert.assertEquals(0, usersBeforeInvalid.size());
        List<User> usersAfterInvalid = userRepository.findByDobAfter(after);
        Assert.assertEquals(0, usersAfterInvalid.size());
        List<User> usersBeforeValid = userRepository.findByDobBefore(after);
        Assert.assertEquals(1, usersBeforeValid.size());
        Assert.assertEquals(user.getId(), usersBeforeValid.get(0).getId());
        List<User> usersAfterValid = userRepository.findByDobAfter(before);
        Assert.assertEquals(1, usersAfterValid.size());
        Assert.assertEquals(user.getId(), usersAfterValid.get(0).getId());
        List<User> usersBetween = userRepository.findByDobBetween(before, after);
        Assert.assertEquals(1, usersBetween.size());
        Assert.assertEquals(user.getId(), usersBetween.get(0).getId());
        List<User> usersBetweenInvalid = userRepository.findByDobBetween(after, before);
        Assert.assertEquals(0, usersBetweenInvalid.size());

    }

}


