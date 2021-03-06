package com.zpi2016.event.support;

import com.zpi2016.event.domain.Category;
import com.zpi2016.event.domain.Event;
import com.zpi2016.event.service.EventService;
import com.zpi2016.location.domain.Location;
import com.zpi2016.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Sandra on 2016-04-01.
 */

@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event createEvent(@RequestBody @Valid final Event Event) {
        return eventService.save(Event);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Event> getEvents() {
        return eventService.findAll();
    }

    @RequestMapping(value = "/startTime", method = RequestMethod.GET)
    public Iterable<Event> getEvents(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date) {
        return eventService.findAllAfter(date);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public Category[] getCategories() {
        return Category.values();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Event getEventById(@PathVariable UUID id) {
        return eventService.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event updateEventById(@RequestBody final Event event, @PathVariable UUID id) {
        return eventService.update(event, id);
    }

    @RequestMapping(value = "/{id}/participants", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event registerParticipant(@RequestBody final User user, @PathVariable UUID id) {
        return eventService.registerParticipant(user, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteEventWithId(@PathVariable UUID id) {
        eventService.delete(id);
    }

    @RequestMapping(value = "/{id}/participants/{userId}", method = RequestMethod.DELETE)
    public Event unregisterParticipant(@PathVariable UUID id, @PathVariable UUID userId) {
        return eventService.unregisterParticipant(id, userId);
    }

    @RequestMapping(value = "/{id}/place", method = RequestMethod.GET)
    public Location getPlaceOfEventWithId(@PathVariable UUID id) {
        return eventService.findPlace(id);
    }

    @RequestMapping(value = "/{id}/eventPlace", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Location updateAddressOfEventWithId(@RequestBody final Location place, @PathVariable UUID id) {
        return eventService.updatePlace(place, id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleEventAlreadyExistsException(EventAlreadyExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEventNotFoundException(EventNotFoundException e) {
        return e.getMessage();
    }


}
