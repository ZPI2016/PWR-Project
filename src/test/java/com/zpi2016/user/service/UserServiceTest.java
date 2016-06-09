package com.zpi2016.user.service;

import java.util.Date;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.zpi2016.location.domain.Location;
import com.zpi2016.user.domain.User;
import com.zpi2016.user.repository.UserRepository;
import com.zpi2016.user.support.UserNotFoundException;

/**
 * Created by aman on 13.03.16.
 */
@RunWith( MockitoJUnitRunner.class )
public class UserServiceTest
{
    private static final UUID SAMPLE_USER_ID = new UUID( 1L, 1L );
    
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User sampleUser;

    @Mock
    private Location sampleLocation;

    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "p@55w0rd";
    private static final String FIRST_NAME = "Tyler";
    private static final String LAST_NAME = "Durden";
    private static final String EMAIL = "mail@example.com";
    private static final Date DOB = new Date();
    private static final Location ADDRESS = new Location(50.0, 45.0);
    private static final Float RADIUS = 23.0f;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(userService, "salt", "salty");
        setupLocation();
        setupSampleUser();
    }

    @Test
    public void shouldSaveNewUser()
    {
        final User savedUser = stubRepositoryToReturnUserOnSave();
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        User user = new User.Builder(USERNAME, PASSWORD, EMAIL, DOB, address, RADIUS)
                .withFirstName(FIRST_NAME).withLastName(LAST_NAME).build();
        final User returnedUser = userService.save(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Assert.assertEquals("Returned user should come from the repository", savedUser, returnedUser);
    }

    @Test
    public void whenFoundSampleUser_shouldDeleteUser()
    {
        when( userRepository.exists( SAMPLE_USER_ID ) ).thenReturn( true );
        userService.delete( SAMPLE_USER_ID );
        verify( userRepository ).delete( SAMPLE_USER_ID );
    }

    @Test
    public void whenUpdateNotExistingUser_shouldThrowException()
    {
        when( userRepository.exists( SAMPLE_USER_ID ) ).thenReturn( true );
        when( userRepository.findByUsernameOrEmail( anyString(), anyString() ) ).thenReturn( sampleUser );
        userService.delete( SAMPLE_USER_ID );
        verify( userRepository ).delete( SAMPLE_USER_ID );
    }

    @Test
    public void whenUpdateExistingUser_shouldUpdateUserData()
    {
        when( userRepository.exists( SAMPLE_USER_ID ) ).thenReturn( true );
        when( userRepository.findByUsernameOrEmail( anyString(), anyString() ) ).thenReturn( sampleUser );
        User existing = mock( User.class );
        when( userService.findOne( SAMPLE_USER_ID ) ).thenReturn( existing );
        doNothing().when( existing ).updateWithPropertiesFrom( sampleUser );
        User returned = userService.update( sampleUser, SAMPLE_USER_ID );
        Assert.assertEquals( existing, returned );
    }

    @Test( expected = UserNotFoundException.class )
    public void whenNotFoundSampleUser_shouldThrowException()
    {
        when( userRepository.exists( SAMPLE_USER_ID ) ).thenReturn( false );
        userService.delete( SAMPLE_USER_ID );
        verify( userRepository, never() ).delete( SAMPLE_USER_ID );
    }


    private void setupSampleUser()
    {
        when( sampleUser.getId() ).thenReturn( SAMPLE_USER_ID );
        doNothing().when( sampleUser ).updateWithPropertiesFrom( any( User.class ) );
    }


    private void setupLocation()
    {
        when( sampleLocation.getGeoLatitude() ).thenReturn( 10.5 );
        when( sampleLocation.getGeoLongitude() ).thenReturn( 20.5 );
    }


    private User stubRepositoryToReturnUserOnSave()
    {
        User user = new User();
        when(userRepository.save(Mockito.any(User.class))).thenReturn( user );
        return user;
    }

}

