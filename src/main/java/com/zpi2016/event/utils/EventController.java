package com.zpi2016.event.utils;

import com.zpi2016.event.domain.Category;
import com.zpi2016.event.domain.Event;
import com.zpi2016.event.service.EventService;
import com.zpi2016.location.domain.Location;
import com.zpi2016.user.domain.User;
import com.zpi2016.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sandra on 2016-04-01.
 */

@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

//    @RequestMapping("/user")
//    public String showMockedUser(){
//        return "/html/user.html";
//    }
//
//    @RequestMapping("/event")
//    public String showAllEvents(){
//        return "/html/event.html";
//    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event createEvent(@RequestBody @Valid final Event Event) throws EventNotFoundException {
        return eventService.save(Event);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Event> getEvents() throws EventNotFoundException {
        //String title, Category category, Date startTime, Location place, User creator
        eventService.save(new Event.Builder("Sample Title", Category.DANCING,new Date(), new Location(50.5f, 20.1f),userService.findByName("Jan")).build());
        return eventService.findAll();
    }



    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Event getEventById(@PathVariable UUID id) {
        return eventService.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event updateEventById(@RequestBody final Event event, @PathVariable UUID id) throws EventNotFoundException {
        return eventService.update(event, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteEventWithId(@PathVariable UUID id) throws EventNotFoundException {
        eventService.delete(id);
    }


    @RequestMapping(value = "/{id}/place", method = RequestMethod.GET)
    public Location getPlaceOfEventWithId(@PathVariable UUID id) throws EventNotFoundException {
        return eventService.findPlace(id);
    }

    @RequestMapping(value = "/{id}/eventPlace", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Location updateAddressOfEventWithId(@RequestBody final Location place, @PathVariable UUID id) throws EventNotFoundException {
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
