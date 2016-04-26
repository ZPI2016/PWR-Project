package com.zpi2016.user.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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


    @Before
    public void setUp()
    {
        setupLocation();
        setupSampleUser();
    }


    @Test
    public void shouldSaveNewUser()
    {
        final User savedUser = stubRepositoryToReturnUserOnSave();
        final User user = new User();
        final User returnedUser = userService.save( user );
        Mockito.verify( userRepository, Mockito.times( 1 ) ).save( user );
        Assert.assertEquals( "Returned user should come from the repository", savedUser, returnedUser );
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
        doNothing().when( existing ).copy( sampleUser );
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
        doNothing().when( sampleUser ).copy( any( User.class ) );
    }


    private void setupLocation()
    {
        when( sampleLocation.getGeoLatitude() ).thenReturn( 10.5 );
        when( sampleLocation.getGeoLongitude() ).thenReturn( 20.5 );
    }


    private User stubRepositoryToReturnUserOnSave()
    {
        User user = new User();
        when( userRepository.save( Mockito.any( User.class ) ) ).thenReturn( user );
        return user;
    }

}
