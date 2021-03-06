
package com.zpi2016.user.repository;

import java.util.Date;
import java.util.UUID;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionSystemException;

import com.zpi2016.Application;
import com.zpi2016.location.domain.Location;
import com.zpi2016.location.repository.LocationRepository;
import com.zpi2016.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebIntegrationTest({"com.zpi2016.m33tme.salt=salty"})
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    private static final UUID WRONG_ID = UUID.randomUUID();
    private static final String USERNAME = "USERNAME";
    private static final String FOO = "FOO";
    private static final String PASSWORD = "p@55w0rd";
    private static final String FIRST_NAME = "Tyler";
    private static final String LAST_NAME = "Durden";
    private static final String EMAIL = "mail@example.com";
    private static final Date DOB = new Date();
    private static final Date AFTER_DOB = new Date();
    private static final Date BEFORE_DOB = new Date();
    private static final Location ADDRESS = new Location(50.0, 45.0);
    private static final Location WRONG_ADDRESS = new Location(45.0, 50.0);
    private static final Float RADIUS = 23.0f;

    @BeforeClass
    public static void setThingsUp() {
        AFTER_DOB.setTime(DOB.getTime() + 100000000l);
        BEFORE_DOB.setTime(DOB.getTime() - 100000000l);
    }

    @Before
    public void setUp(){
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        User user = new User.Builder(USERNAME, PASSWORD, EMAIL, DOB, address, RADIUS)
                .withFirstName(FIRST_NAME).withLastName(LAST_NAME).build();
        userRepository.save(user);
    }

    @After
    public void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    public void shouldSaveUser() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        userRepository.save(new User.Builder(USERNAME + FOO, PASSWORD, EMAIL + FOO, DOB, address, RADIUS)
                .withFirstName(FIRST_NAME).withLastName(LAST_NAME).build());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveUserWithoutUsername() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
       userRepository.save(new User.Builder(null, PASSWORD, EMAIL + FOO, DOB, address, RADIUS).build());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveUserWithoutUniqueUsername() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        userRepository.save(new User.Builder(USERNAME, PASSWORD, EMAIL + FOO, DOB, address, RADIUS).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void shouldNotSaveUserWithoutPassword() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        userRepository.save(new User.Builder(USERNAME + FOO, null, EMAIL + FOO, DOB, address, RADIUS).build());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveUserWithoutEmail() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        userRepository.save(new User.Builder(USERNAME + FOO, PASSWORD, null, DOB, address, RADIUS).build());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveUserWithoutUniqueEmail() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        userRepository.save(new User.Builder(USERNAME + FOO, PASSWORD, EMAIL, DOB, address, RADIUS).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void shouldNotSaveUserWithoutDob() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        userRepository.save(new User.Builder(USERNAME + FOO, PASSWORD, EMAIL + FOO, null, address, RADIUS).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void shouldNotSaveUserWithoutAddress() {
        userRepository.save(new User.Builder(USERNAME + FOO, PASSWORD, EMAIL + FOO, DOB, null, RADIUS).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void shouldNotSaveUserWithoutRadius() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        userRepository.save(new User.Builder(USERNAME + FOO, PASSWORD, EMAIL + FOO, DOB, address, null).build());
    }

    @Test
    public void shouldDeleteUser() {
        userRepository.delete(userRepository.findAll().iterator().next());
        Assert.assertFalse(userRepository.findAll().iterator().hasNext());
    }

    @Test
    public void shouldNotDeleteUser() {
        userRepository.delete(new User.Builder(USERNAME + FOO, PASSWORD, EMAIL + FOO, DOB, ADDRESS, RADIUS).build());
    }

    @Test
    public void shouldDeleteUserById() {
        userRepository.delete(userRepository.findAll().iterator().next().getId());
        Assert.assertFalse(userRepository.findAll().iterator().hasNext());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldNotDeleteUserById() throws Exception {
        userRepository.delete(WRONG_ID);
    }

    @Test
    public void shouldFindUsers() {
        Assert.assertTrue(userRepository.findAll().iterator().hasNext());
    }

    @Test
    public void shouldNotFindUsers() {
        userRepository.deleteAll();
        Assert.assertFalse(userRepository.findAll().iterator().hasNext());
    }

    @Test
    public void shouldFindUser() {
        Assert.assertNotNull(userRepository.findOne(userRepository.findAll().iterator().next().getId()));
    }

    @Test
    public void shouldNotFindUser() {
        Assert.assertNull(userRepository.findOne(WRONG_ID));
    }

    @Test
    public void shouldFindUserByUsername() {
        Assert.assertNotNull(userRepository.findByUsername(USERNAME));
    }

    @Test
    public void shouldNotFindUserByUsername() {
        Assert.assertNull(userRepository.findByUsername(FOO));
    }

    @Test
    public void shouldFindUserByEmail() {
        Assert.assertNotNull(userRepository.findByEmail(EMAIL));
    }

    @Test
    public void shouldNotFindUserByEmail() {
        Assert.assertNull(userRepository.findByEmail(FOO));
    }

    @Test
    public void shouldFindUserByUsernameOrEmail() {
        Assert.assertNotNull(userRepository.findByUsernameOrEmail(USERNAME,EMAIL));
    }

    @Test
    public void shouldFindUserByUsernameOrEmailInvalidUsername() {
        Assert.assertNotNull(userRepository.findByUsernameOrEmail(FOO,EMAIL));
    }

    @Test
    public void shouldFindUserByUsernameOrEmailInvalidEmail() {
        Assert.assertNotNull(userRepository.findByUsernameOrEmail(USERNAME,FOO));
    }

    @Test
    public void shouldNotFindUserByUsernameOrEmail() {
        Assert.assertNull(userRepository.findByUsernameOrEmail(FOO,FOO));
    }

    @Test
    public void shouldFindUserByFirstName() {
        Assert.assertFalse(userRepository.findByFirstName(FIRST_NAME).isEmpty());
    }

    @Test
    public void shouldNotFindUserByFirstName() {
        Assert.assertTrue(userRepository.findByFirstName(FOO).isEmpty());
    }

    @Test
    public void shouldFindUserByLastName() {
        Assert.assertFalse(userRepository.findByLastName(LAST_NAME).isEmpty());
    }

    @Test
    public void shouldNotFindUserByLastName() {
        Assert.assertTrue(userRepository.findByLastName(FOO).isEmpty());
    }

    @Test
    public void shouldFindUserByFirstNameAndLastName() {
        Assert.assertFalse(userRepository.findByFirstNameAndLastName(FIRST_NAME, LAST_NAME).isEmpty());
    }

    @Test
    public void shouldNotFindUserByFirstNameAndLastName() {
        Assert.assertTrue(userRepository.findByFirstNameAndLastName(FIRST_NAME, FOO).isEmpty());
    }

    @Test
    public void shouldFindUserByFirstNameOrLastName() {
        Assert.assertFalse(userRepository.findByFirstNameOrLastName(FIRST_NAME, FOO).isEmpty());
    }

    @Test
    public void shouldNotFindUserByFirstNameOrLastName() {
        Assert.assertTrue(userRepository.findByFirstNameAndLastName(FOO, FOO).isEmpty());
    }

    @Test
    public void shouldFindUserByDob() {
        Assert.assertFalse(userRepository.findByDob(DOB).isEmpty());
    }

    @Test
    public void shouldNotFindUserByDob() {
        Assert.assertTrue(userRepository.findByDob(AFTER_DOB).isEmpty());
    }

    @Test
    public void shouldFindUserByDobAfter() {
        Assert.assertFalse(userRepository.findByDobAfter(BEFORE_DOB).isEmpty());
    }

    @Test
    public void shouldNotFindUserByDobAfter() {
        Assert.assertTrue(userRepository.findByDobAfter(AFTER_DOB).isEmpty());
    }

    @Test
    public void shouldFindUserByDobBefore() {
        Assert.assertFalse(userRepository.findByDobBefore(AFTER_DOB).isEmpty());
    }

    @Test
    public void shouldNotFindUserByDobBefore() {
        Assert.assertTrue(userRepository.findByDobBefore(BEFORE_DOB).isEmpty());
    }

    @Test
    public void shouldFindUserByDobBetween() {
        Assert.assertFalse(userRepository.findByDobBetween(BEFORE_DOB, AFTER_DOB).isEmpty());
    }

    @Test
    public void shouldNotFindUserByDobBetween() {
        Assert.assertTrue(userRepository.findByDobBetween(BEFORE_DOB, BEFORE_DOB).isEmpty());
    }

    @Test
    public void shouldFindUserByAddress() {
        Location address = userRepository.findAll().iterator().next().getAddress();
        Assert.assertFalse(userRepository.findByAddress(address).isEmpty());
    }

    @Test
    public void shouldNotFindUserByAddress() {
        Location address = locationRepository.save(new Location(WRONG_ADDRESS.getGeoLongitude(), WRONG_ADDRESS.getGeoLatitude()));
        Assert.assertTrue(userRepository.findByAddress(address).isEmpty());
    }

}


