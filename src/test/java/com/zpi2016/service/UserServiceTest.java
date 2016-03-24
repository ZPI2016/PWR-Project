package com.zpi2016.service;

import com.zpi2016.model.User;
import com.zpi2016.repository.UserRepository;
import com.zpi2016.service.user.UserService;
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
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldSaveNewUser() {
        final User savedUser = stubRepositoryToReturnUserOnSave();
        final User user = new User();
        final User returnedUser = userService.save(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Assert.assertEquals("Returned user should come from the repository", savedUser, returnedUser);
    }

    private User stubRepositoryToReturnUserOnSave() {
        User user = new User();
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        return user;
    }

}

