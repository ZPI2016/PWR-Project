package com.zpi2016.repository;

import com.zpi2016.Application;
import com.zpi2016.model.Category;
import com.zpi2016.model.Event;
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

/**
 * Created by kuba on 03.03.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class EventRepositoryTest {

    Event event;
    EventRepository eventRepository;
    UserRepository userRepository;
    LocationRepository locationRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Before
    public void init(){
        event = new Event();
        event.setCategory(Category.SPORTS);
        event.setStartTime(new Date(1005260400000l));

        Location wroclaw = new Location();
        wroclaw.setGeoLatitude(51.107885f);
        wroclaw.setGeoLongitude(17.038538f);
        locationRepository.save(wroclaw);
        event.setPlace(wroclaw);

        User eventCreator = new User();
        eventCreator.setUsername("username");
        eventCreator.setPassword("password");
        eventCreator.setEmail("email@email.com");
        eventCreator.setAddress(wroclaw);
        eventCreator.setDob(new Date());
        userRepository.save(eventCreator);
        event.setCreator(eventCreator);

        event.setDeadline(new Date(952036630000l));
        event.setMinParticipants(0);
        event.setMaxParticipants(10);
    }

    @After
    public void clean(){
        Location eventLocation = locationRepository.findOne(event.getPlace().getId());
        User eventCreator = userRepository.findOne(event.getCreator().getId());
        eventRepository.delete(event.getId());
        userRepository.delete(eventCreator.getId());
        locationRepository.delete(eventLocation.getId());
    }

    /**
     * Basic test of functionality of EventRespository
     * provided by CrudRepository interface
     */
    @Test
    public void basicEventTest(){

        // the id should be null before saving the object to db
        Assert.assertNull(event.getId());

        eventRepository.save(event);

        // the id should be now initialized
        Assert.assertNotNull(event.getId());

        // fetching product from db
        Event fetchedEvent = eventRepository.findOne(event.getId());

        // checking if fetching was succesfull
        Assert.assertNotNull(fetchedEvent);

        // checking the equality
        Assert.assertEquals(event.getId(), fetchedEvent.getId());

        // checking the updating functionality
        fetchedEvent.setCategory(Category.DANCING);
        eventRepository.save(fetchedEvent);
        Event fetchedUpdatedEvent = eventRepository.findOne(fetchedEvent.getId());
        Assert.assertEquals(event.getId(), fetchedUpdatedEvent.getId());
        Assert.assertNotEquals(event.getCategory(), fetchedUpdatedEvent.getCategory());

        // checking if the entries are not duplicated
        Iterable<Event> fetchedEvents = eventRepository.findAll();
        int eventsCount = 0;
        for (Event e : fetchedEvents)
            eventsCount++;
        Assert.assertEquals(1, eventsCount);

    }

    @Test
    public void variousEventQueriesTest(){
        eventRepository.save(event);

        List<Event> eventsByCategory = eventRepository.findByCategory(Category.SPORTS);
        Assert.assertEquals(1, eventsByCategory.size());
        Assert.assertEquals(event.getId(), eventsByCategory.get(0).getId());

        Date startTime = new Date(1005260400000l);
        Date deadline = new Date(952036630000l);
        Date after = new Date(1267569430000l);
        Date before = new Date(852036630000l);

        List<Event> eventsByStartTime = eventRepository.findByStartTime(startTime);
        Assert.assertEquals(1, eventsByStartTime.size());
        Assert.assertEquals(event.getId(), eventsByStartTime.get(0).getId());

        List<Event> eventsBeforeStartTimeValid = eventRepository.findByStartTimeBefore(after);
        Assert.assertEquals(1, eventsBeforeStartTimeValid.size());
        Assert.assertEquals(event.getId(), eventsBeforeStartTimeValid.get(0).getId());

        List<Event> eventsBeforeStartTimeInvalid = eventRepository.findByStartTimeBefore(before);
        Assert.assertEquals(0, eventsBeforeStartTimeInvalid.size());

        List<Event> eventsAfterStartTimeValid = eventRepository.findByStartTimeAfter(before);
        Assert.assertEquals(1, eventsAfterStartTimeValid.size());
        Assert.assertEquals(event.getId(), eventsAfterStartTimeValid.get(0).getId());

        List<Event> eventsAfterStartTimeInvalid = eventRepository.findByStartTimeAfter(after);
        Assert.assertEquals(0, eventsAfterStartTimeInvalid.size());

        List<Event> eventsBetweenStartTimeValid = eventRepository.findByStartTimeBetween(before, after);
        Assert.assertEquals(1, eventsBetweenStartTimeValid.size());
        Assert.assertEquals(event.getId(), eventsBetweenStartTimeValid.get(0).getId());

        List<Event> eventsBetweenStartTimeInvalid = eventRepository.findByStartTimeBetween(after, before);
        Assert.assertEquals(0, eventsBetweenStartTimeInvalid.size());

        List<Event> eventsByDeadline = eventRepository.findByDeadline(deadline);
        Assert.assertEquals(1, eventsByDeadline.size());
        Assert.assertEquals(event.getId(), eventsByDeadline.get(0).getId());

        List<Event> eventsBeforeDeadlineValid = eventRepository.findByDeadlineBefore(after);
        Assert.assertEquals(1, eventsBeforeDeadlineValid.size());
        Assert.assertEquals(event.getId(), eventsBeforeDeadlineValid.get(0).getId());

        List<Event> eventsBeforeDeadlineInvalid = eventRepository.findByDeadlineBefore(before);
        Assert.assertEquals(0, eventsBeforeDeadlineInvalid.size());

        List<Event> eventsAfterDeadlineValid = eventRepository.findByDeadlineAfter(before);
        Assert.assertEquals(1, eventsAfterDeadlineValid.size());
        Assert.assertEquals(event.getId(), eventsAfterDeadlineValid.get(0).getId());

        List<Event> eventsAfterDeadlineInvalid = eventRepository.findByDeadlineAfter(after);
        Assert.assertEquals(0, eventsAfterDeadlineInvalid.size());

        List<Event> eventsBetweenDeadlineValid = eventRepository.findByDeadlineBetween(before, after);
        Assert.assertEquals(1, eventsBetweenDeadlineValid.size());
        Assert.assertEquals(event.getId(), eventsBetweenDeadlineValid.get(0).getId());

        List<Event> eventsBetweenDeadlineInvalid = eventRepository.findByDeadlineBetween(after, before);
        Assert.assertEquals(0, eventsBetweenDeadlineInvalid.size());

        Location wroclaw = event.getPlace();
        List<Event> eventsByPlace = eventRepository.findByPlace(wroclaw);
        Assert.assertEquals(1, eventsByPlace.size());
        Assert.assertEquals(event.getId(), eventsByPlace.get(0).getId());

        User userCreator = event.getCreator();
        List<Event> eventsByCreatorValid = eventRepository.findByCreator(userCreator);
        Assert.assertEquals(1, eventsByCreatorValid.size());
        Assert.assertEquals(event.getId(), eventsByCreatorValid.get(0).getId());

        List<Event> eventsByMinParticipants = eventRepository.findByMinParticipants(0);
        Assert.assertEquals(1, eventsByMinParticipants.size());
        Assert.assertEquals(event.getId(), eventsByMinParticipants.get(0).getId());

        List<Event> eventsByMaxParticipants = eventRepository.findByMaxParticipants(10);
        Assert.assertEquals(1, eventsByMaxParticipants.size());
        Assert.assertEquals(event.getId(), eventsByMaxParticipants.get(0).getId());

        List<Event> eventsByParticipantsBetween = eventRepository.findByParticipantsBetween(0, 10);
        Assert.assertEquals(1, eventsByParticipantsBetween.size());
        Assert.assertEquals(event.getId(), eventsByParticipantsBetween.get(0).getId());
    }

}
