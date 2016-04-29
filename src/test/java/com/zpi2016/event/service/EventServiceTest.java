package com.zpi2016.event.service;

import com.zpi2016.event.domain.Event;
import com.zpi2016.event.repository.EventRepository;
import com.zpi2016.event.utils.EventNotFoundException;
import com.zpi2016.location.domain.Location;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    private static final UUID SAMPLE_EVENT_ID = new UUID(1L, 1L);
    private static final double SAMPLE_VALID_COORDINATE = 10.5;
    private static final double SAMPLE_INVALID_COORDINATE = -10.5;
    private static final int SAMPLE_NUMBER_OF_PARTICIPANTS = 5;

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private Event event;

    @Mock
    private Location validLocation;

    @Mock
    private Location invalidLocation;


    @Before
    public void setUp() throws Exception {

        stubEvent();
        stubValidLocation();
        stubInvalidLocation();
    }

    private void stubEvent() {
        when(event.getId()).thenReturn(SAMPLE_EVENT_ID);
        doNothing().when(event).copy(any(Event.class));
    }

    @Test
    public void testSave_shouldSaveNewEvent() throws Exception, EventNotFoundException {

        stubRepositoryToReturnEventOnSave();
        Event savedEvent = eventService.save(event);
        Mockito.verify(eventRepository, Mockito.times(1)).save(event);
        Assert.assertEquals("Returned user should come from the repository", savedEvent, event);

    }

    @Test
    public void testSave_shouldNotSaveDuplocatedEvent() throws Exception, EventNotFoundException {

        stubRepositoryToReturnEventOnSave();
        Event savedEvent = eventService.save(event);
        Mockito.verify(eventRepository).save(event);
        Assert.assertEquals("Returned user should come from the repository", savedEvent, event);

    }

    @Test
    public void testFindOne_shouldReturnFoundEvent() throws Exception, EventNotFoundException {

        stubRepositoryToReturnEventOnSave();
        Event savedEvent = eventService.save(event);
        Mockito.verify(eventRepository).save(event);
        when(eventRepository.findOne(event.getId())).thenReturn(event);
        Event found = eventService.findOne(event.getId());
        Assert.assertEquals(savedEvent, found);

    }

    @Test
    public void testFindOne_shouldNotReturnNonExistingEvent() throws Exception {

        when(event.getId()).thenReturn(null);
        Event found = eventService.findOne(event.getId());
        Assert.assertNull(found);

    }

    @Test
    public void testFindAll() throws Exception {

        when(eventRepository.findAll()).thenReturn(Arrays.asList());
        Iterable<Event> events = eventService.findAll();
        Assert.assertNotNull("List should not be null", events);
        Assert.assertTrue(events instanceof List);

    }

    @Test
    public void testUpdate() throws Exception {

    }


    @Test(expected = EventNotFoundException.class)
    public void testUpdate_shouldThrowExceptionIfNoUserWithIdExists() throws Exception, EventNotFoundException {

        eventService.update(event, event.getId());

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testFindPlace() throws Exception {

    }

    @Test
    public void testUpdatePlace() throws Exception, EventNotFoundException {

        when(eventRepository.exists(event.getId())).thenReturn(true);
        Event eventWithChangedPlace = mock(Event.class);
        eventWithChangedPlace.setPlace(validLocation);
        when(eventRepository.findOne(event.getId())).thenReturn(event);
        Event updated = eventService.update(eventWithChangedPlace, event.getId());
        Mockito.verify(eventRepository, Mockito.times(1)).findOne(event.getId());
        Assert.assertEquals(updated, event);

    }

    @Test
    public void testUpdateTime() throws Exception {

    }

    @Test
    public void testUpdateParticipantsNumber() throws Exception, EventNotFoundException {

        when(eventRepository.exists(event.getId())).thenReturn(true);
        event.setMaxParticipants(SAMPLE_NUMBER_OF_PARTICIPANTS);
        when(eventRepository.findOne(event.getId())).thenReturn(event);
        eventService.update(event, event.getId());
        Mockito.verify(eventRepository, Mockito.times(1)).findOne(event.getId());

    }

    private void stubValidLocation() {
        when(validLocation.getGeoLatitude()).thenReturn(SAMPLE_VALID_COORDINATE);
        when(validLocation.getGeoLongitude()).thenReturn(SAMPLE_VALID_COORDINATE);
    }

    private void stubInvalidLocation() {
        when(invalidLocation.getGeoLatitude()).thenReturn(SAMPLE_INVALID_COORDINATE);
        when(invalidLocation.getGeoLongitude()).thenReturn(SAMPLE_INVALID_COORDINATE);
    }

    private void stubRepositoryToReturnEventOnSave() {
        when(eventRepository.save(any(Event.class))).thenReturn(event);

    }
}