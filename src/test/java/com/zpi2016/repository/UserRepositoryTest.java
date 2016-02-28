
package com.zpi2016.repository;

import com.zpi2016.Application;
import com.zpi2016.model.Location;
import com.zpi2016.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class UserRepositoryTest {

    private UserRepository userRepository;
    private LocationRepository locationRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Basic test of saving functionality of UserRepository
     */
    @Test
    public void testSaveUser() {

        // setup the user
        User user = new User();
        user.setFirstName("Jan");
        user.setLastName("Nowak");
        user.setUsername("jurny_jan");
        user.setPassword("haslo");
        user.setEmail("jan.nowak@dummy.com");
        Date dob = new Date();
        dob.setTime(1005260400000l);
        user.setDOB(dob);

        Location wroclaw = new Location();
        wroclaw.setGeoLatitude(51.107885f);
        wroclaw.setGeoLongitude(17.038538f);
        locationRepository.save(wroclaw);

        user.setAddress(wroclaw);

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

}


