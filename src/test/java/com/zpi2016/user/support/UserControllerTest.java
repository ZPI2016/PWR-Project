package com.zpi2016.user.support;

import com.zpi2016.location.domain.Location;
import com.zpi2016.user.domain.User;
import com.zpi2016.user.service.UserService;
import com.zpi2016.user.support.UserController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

/**
 * Created by aman on 13.03.16.
 */

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "p@55w0rd";
    private static final String FIRST_NAME = "Tyler";
    private static final String LAST_NAME = "Durden";
    private static final String EMAIL = "mail@example.com";
    private static final Date DOB = new Date();
    private static final Location ADDRESS = new Location(50.0f, 45.0f);
    private static final Float RADIUS = 23.0f;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(userService, "salt", "salty");
    }

    @Test
    public void shouldCreateUser() throws Exception {
        final User savedUser = stubServiceToReturnStoredUser();
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        User user = new User.Builder(USERNAME, PASSWORD, EMAIL, DOB, address, RADIUS)
                .withFirstName(FIRST_NAME).withLastName(LAST_NAME).build();
        User returnedUser = userController.createUser(user);
        // verify user was passed to UserService
        Mockito.verify(userService, Mockito.times(1)).save(user);
        Assert.assertEquals("Returned user should come from the service", savedUser, returnedUser);
    }

    private User stubServiceToReturnStoredUser() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        User user = new User.Builder(USERNAME, PASSWORD, EMAIL, DOB, address, RADIUS)
                .withFirstName(FIRST_NAME).withLastName(LAST_NAME).build();
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user);
        return user;
    }

}