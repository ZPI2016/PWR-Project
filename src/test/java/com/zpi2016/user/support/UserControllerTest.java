package com.zpi2016.user.support;

import com.zpi2016.user.domain.User;
import com.zpi2016.user.service.UserService;
import com.zpi2016.user.support.UserController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by aman on 13.03.16.
 */

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    public void shouldCreateUser() throws Exception {
        final User savedUser = stubServiceToReturnStoredUser();
        final User user = new User();
        User returnedUser = userController.createUser(user);
        // verify user was passed to UserService
        Mockito.verify(userService, Mockito.times(1)).save(user);
        Assert.assertEquals("Returned user should come from the service", savedUser, returnedUser);
    }

    private User stubServiceToReturnStoredUser() {
        final User user = new User();
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user);
        return user;
    }

}