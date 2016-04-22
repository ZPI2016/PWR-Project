package com.zpi2016.event.repository;

import com.zpi2016.Application;
import com.zpi2016.event.domain.Category;
import com.zpi2016.event.domain.Event;
import com.zpi2016.location.domain.Location;
import com.zpi2016.location.repository.LocationRepository;
import com.zpi2016.user.domain.User;
import com.zpi2016.user.repository.UserRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionSystemException;

import java.util.Date;
import java.util.UUID;

/**
 * Created by kuba on 03.03.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class EventRepositoryIT {

    private static final String SAMPLE_TITLE = "Sample title";
    @Autowired
    private EventRepository eventRepository;

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
    private static final Date START_TIME = new Date();;
    private static final Date AFTER_START_TIME = new Date();
    private static final Date BEFORE_START_TIME = new Date();
    private static final Date DEADLINE = new Date();
    private static final Date AFTER_DEADLINE = new Date();
    private static final Date BEFORE_DEADLINE = new Date();
    private static final Location ADDRESS = new Location(50.0f, 45.0f);
    private static final Category CATEGORY = Category.DANCING;
    private static final Category WRONG_CATEGORY = Category.SPORTS;
    private static final Integer MIN_PARTICIPANTS = new Integer(5);
    private static final Integer MAX_PARTICIPANTS = new Integer(7);
    private static final Float RADIUS = 23.0f;

    @BeforeClass
    public static void setThingsUp() {
        AFTER_DEADLINE.setTime(DEADLINE.getTime() + 100000000l);
        BEFORE_DEADLINE.setTime(DEADLINE.getTime() - 100000000l);
        AFTER_START_TIME.setTime(START_TIME.getTime() + 100000000l);
        BEFORE_START_TIME.setTime(START_TIME.getTime() - 100000000l);
    }

    @Before
    public void setUp(){
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        User user = new User.Builder(USERNAME, PASSWORD, EMAIL, DEADLINE, address, RADIUS)
                .withFirstName(FIRST_NAME).withLastName(LAST_NAME).build();
        Event event = new Event.Builder(SAMPLE_TITLE,CATEGORY, START_TIME, address, user).withDeadline(DEADLINE)
                .withMinParticipants(MIN_PARTICIPANTS).withMaxParticipants(MAX_PARTICIPANTS).build();
        user.getCreatedEvents().add(event);
        userRepository.save(user);
    }

    @After
    public void tearDown(){
        eventRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void shouldSaveEvent() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        User user = userRepository.findAll().iterator().next();
        eventRepository.save(new Event.Builder(SAMPLE_TITLE,CATEGORY, START_TIME, address, user).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void shouldNotSaveEventWithoutCategory() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        User user = userRepository.findAll().iterator().next();
        eventRepository.save(new Event.Builder(SAMPLE_TITLE,null, START_TIME, address, user).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void shouldNotSaveEventWithoutStartTime() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        User user = userRepository.findAll().iterator().next();
        eventRepository.save(new Event.Builder(SAMPLE_TITLE,CATEGORY, null, address, user).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void shouldNotSaveEventWithoutPlace() {
        User user = userRepository.findAll().iterator().next();
        eventRepository.save(new Event.Builder(SAMPLE_TITLE,CATEGORY, START_TIME, null, user).build());
    }

    @Test(expected = TransactionSystemException.class)
    public void shouldNotSaveEventWithoutCreator() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        eventRepository.save(new Event.Builder(SAMPLE_TITLE,CATEGORY, START_TIME, address, null).build());
    }

    @Test
    public void shouldDeleteEvent() {
        eventRepository.delete(eventRepository.findAll().iterator().next());
        Assert.assertFalse(eventRepository.findAll().iterator().hasNext());
    }

    @Test
    public void shouldNotDeleteEvent() {
        eventRepository.delete(new Event.Builder(SAMPLE_TITLE,CATEGORY, START_TIME, ADDRESS, userRepository.findAll().iterator().next()).build());
        Assert.assertTrue(eventRepository.findAll().iterator().hasNext());
    }

    @Test
    public void shouldDeleteEventById() {
        eventRepository.delete(eventRepository.findAll().iterator().next().getId());
        Assert.assertFalse(eventRepository.findAll().iterator().hasNext());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldNotDeleteEventById() throws Exception {
        eventRepository.delete(WRONG_ID);
    }

    @Test
    public void shouldFindEvents() {
        Assert.assertTrue(eventRepository.findAll().iterator().hasNext());
    }

    @Test
    public void shouldNotFindEvents() {
        eventRepository.deleteAll();
        Assert.assertFalse(eventRepository.findAll().iterator().hasNext());
    }

    @Test
    public void shouldFindEvent() {
        Assert.assertNotNull(eventRepository.findOne(eventRepository.findAll().iterator().next().getId()));
    }

    @Test
    public void shouldNotFindEvent() {
        Assert.assertNull(eventRepository.findOne(WRONG_ID));
    }

    @Test
    public void shouldFindEventByCategory() {
        Assert.assertFalse(eventRepository.findByCategory(CATEGORY).isEmpty());
    }

    @Test
    public void shouldNotFindEventByCategory() {
        Assert.assertTrue(eventRepository.findByCategory(WRONG_CATEGORY).isEmpty());
    }

    @Test
    public void shouldFindEventByStartTime() {
        Assert.assertFalse(eventRepository.findByStartTime(START_TIME).isEmpty());
    }

    @Test
    public void shouldNotFindEventByStartTime() {
        Assert.assertTrue(eventRepository.findByStartTime(BEFORE_START_TIME).isEmpty());
    }

    @Test
    public void shouldFindEventByStartTimeBefore() {
        Assert.assertFalse(eventRepository.findByStartTimeBefore(AFTER_START_TIME).isEmpty());
    }

    @Test
    public void shouldNotFindEventByStartTimeBefore() {
        Assert.assertTrue(eventRepository.findByStartTimeBefore(BEFORE_START_TIME).isEmpty());
    }

    @Test
    public void shouldFindEventByStartTimeAfter() {
        Assert.assertFalse(eventRepository.findByStartTimeAfter(BEFORE_START_TIME).isEmpty());
    }

    @Test
    public void shouldNotFindEventByStartTimeAfter() {
        Assert.assertTrue(eventRepository.findByStartTimeAfter(AFTER_START_TIME).isEmpty());
    }

    @Test
    public void shouldFindEventByStartTimeBetween() {
        Assert.assertFalse(eventRepository.findByStartTimeBetween(BEFORE_START_TIME, AFTER_START_TIME).isEmpty());
    }

    @Test
    public void shouldNotFindEventByStartTimeBetween() {
        Assert.assertTrue(eventRepository.findByStartTimeBetween(BEFORE_START_TIME, BEFORE_START_TIME).isEmpty());
    }

    @Test
    public void shouldFindEventByCreator() {
        User creator = eventRepository.findAll().iterator().next().getCreator();
        Assert.assertFalse(eventRepository.findByCreator(creator).isEmpty());
    }

    @Test
    public void shouldNotFindEventByCreator() {
        Location address = new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude());
        User creator = userRepository.save(new User.Builder(USERNAME + FOO, PASSWORD, EMAIL + FOO, DEADLINE, address, RADIUS).build());
        Assert.assertTrue(eventRepository.findByCreator(creator).isEmpty());
    }

    @Test
    public void shouldFindEventByPlace() {
        Location place = eventRepository.findAll().iterator().next().getPlace();
        Assert.assertFalse(eventRepository.findByPlace(place).isEmpty());
    }

    @Test
    public void shouldNotFindEventByPlace() {
        Location place = locationRepository.save(new Location(ADDRESS.getGeoLongitude(), ADDRESS.getGeoLatitude()));
        Assert.assertTrue(eventRepository.findByPlace(place).isEmpty());
    }

    @Test
    public void shouldFindEventByDeadline() {
        Assert.assertFalse(eventRepository.findByDeadline(DEADLINE).isEmpty());
    }

    @Test
    public void shouldNotFindEventByDeadline() {
        Assert.assertTrue(eventRepository.findByDeadline(BEFORE_DEADLINE).isEmpty());
    }

    @Test
    public void shouldFindEventByDeadlineBefore() {
        Assert.assertFalse(eventRepository.findByDeadlineBefore(AFTER_DEADLINE).isEmpty());
    }

    @Test
    public void shouldNotFindEventByDeadlineBefore() {
        Assert.assertTrue(eventRepository.findByDeadlineBefore(BEFORE_DEADLINE).isEmpty());
    }

    @Test
    public void shouldFindEventByDeadlineAfter() {
        Assert.assertFalse(eventRepository.findByDeadlineAfter(BEFORE_DEADLINE).isEmpty());
    }

    @Test
    public void shouldNotFindEventByDeadlineAfter() {
        Assert.assertTrue(eventRepository.findByDeadlineAfter(AFTER_DEADLINE).isEmpty());
    }

    @Test
    public void shouldFindEventByDeadlineBetween() {
        Assert.assertFalse(eventRepository.findByDeadlineBetween(BEFORE_DEADLINE, AFTER_DEADLINE).isEmpty());
    }

    @Test
    public void shouldNotFindEventByDeadlineBetween() {
        Assert.assertTrue(eventRepository.findByDeadlineBetween(BEFORE_DEADLINE, BEFORE_DEADLINE).isEmpty());
    }

    @Test
    public void shouldFindEventByMinParticipants() {
        Assert.assertFalse(eventRepository.findByMinParticipants(MIN_PARTICIPANTS).isEmpty());
    }

    @Test
    public void shouldNotFindEventByMinParticipants() {
        Assert.assertTrue(eventRepository.findByMinParticipants(MIN_PARTICIPANTS - 1).isEmpty());
    }

    @Test
    public void shouldFindEventByMaxParticipants() {
        Assert.assertFalse(eventRepository.findByMaxParticipants(MAX_PARTICIPANTS).isEmpty());
    }

    @Test
    public void shouldNotFindEventByMaxParticipants() {
        Assert.assertTrue(eventRepository.findByMaxParticipants(MAX_PARTICIPANTS + 1).isEmpty());
    }

    @Test
    public void shouldFindEventByParticipantsBetween() {
        Assert.assertFalse(eventRepository.findByParticipantsBetween(MIN_PARTICIPANTS, MAX_PARTICIPANTS).isEmpty());
    }

    @Test
    public void shouldNotFindEventByParticipantsBetween() {
        Assert.assertTrue(eventRepository.findByParticipantsBetween(MIN_PARTICIPANTS, MAX_PARTICIPANTS - 1).isEmpty());
    }

}
