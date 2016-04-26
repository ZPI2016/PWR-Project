package com.zpi2016.user.service;

import com.zpi2016.location.domain.Location;
import com.zpi2016.user.domain.User;
import com.zpi2016.user.repository.UserRepository;
import com.zpi2016.user.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * Created by aman on 13.03.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

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
    public void shouldSaveNewUser() {
        final User savedUser = stubRepositoryToReturnUserOnSave();
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        User user = new User.Builder(USERNAME, PASSWORD, EMAIL, DOB, address, RADIUS)
                .withFirstName(FIRST_NAME).withLastName(LAST_NAME).build();
        final User returnedUser = userService.save(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Assert.assertEquals("Returned user should come from the repository", savedUser, returnedUser);
    }

    private User stubRepositoryToReturnUserOnSave() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        User user = new User.Builder(USERNAME, PASSWORD, EMAIL, DOB, address, RADIUS)
                .withFirstName(FIRST_NAME).withLastName(LAST_NAME).build();
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        return user;
    }

}

