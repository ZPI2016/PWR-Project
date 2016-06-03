package com.zpi2016.event.service;

import com.google.common.base.Preconditions;
import com.zpi2016.core.common.service.GenericService;
import com.zpi2016.event.domain.Event;
import com.zpi2016.event.repository.EventRepository;
import com.zpi2016.event.support.EventNotFoundException;
import com.zpi2016.location.domain.Location;
import com.zpi2016.user.domain.User;
import com.zpi2016.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Set;
import java.util.UUID;


/**
 * Created by Sandra on 2016-04-01.
 */

@Service
public class EventService implements GenericService<Event> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository repository;


    //todo: think about constraints when it comes to event creation (same name and creator as uniqe guarantee)
    @Override
    @Transactional
    public Event save(final Event event) {
       return repository.save(event);
    }

    @Override
    public Event findOne(final UUID id) {
        return repository.findOne(id);
    }

    @Override
    public Iterable<Event> findAll() {
        return repository.findAll();
    }

    public Iterable<Event> findAllAfter(Date date) {
        return repository.findByStartTimeAfter(date);
    }

    @Override
    @Transactional
    public Event update(Event event, UUID id) {
        checkIfEventExists(id);
        Event existing = findOne(id);
        existing.copy(event);
        return existing;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        checkIfEventExists(id);
        repository.delete(id);
    }

    @Transactional
    public Location findPlace(UUID id) {
        checkIfEventExists(id);
        return findOne(id).getPlace();
    }

    @Transactional
    public Location updatePlace(Location newPlace, UUID id) {
        checkIfEventExists(id);
        Location currentPlace = findOne(id).getPlace();
        currentPlace.updateWithPropertiesFrom(newPlace);
        return currentPlace;
    }

    @Transactional
    public Event unregisterParticipant(UUID id, UUID userId) {
        checkIfEventExists(id);
        Event event = findOne(id);
        final Set<User> participants = event.getParticipants();
        User toDelete = null;
        for (User user : participants) {
            if (user.getId().equals(userId)) {
                toDelete = user;
                break;
            }
        }
        participants.remove(toDelete);
        return event;
    }

    @Transactional
    public Event registerParticipant(User user, UUID id) {
        checkIfEventExists(id);
        Event event = findOne(id);
        User fromDB = userRepository.findOne(user.getId());
        final Set<User> participants = event.getParticipants();
        if (!participants.contains(fromDB))
            participants.add(fromDB);
        return event;
    }

    @Transactional
    public Date updateTime(Date newStartTime, UUID id) {
        //todo: change this. Find a solution not to create object every time we want to update time.
        Preconditions.checkArgument(newStartTime.after(new Date()), "Date of event start cannot be past one!");
        checkIfEventExists(id);
            findOne(id).setStartTime(newStartTime);
        return newStartTime;
    }

    @Transactional
    public Event updateParticipantsNumber(int newMinParticipants, int newMaxParticipants, UUID id) {
        Preconditions.checkArgument(newMinParticipants>0, "Number of minimum participants must be a positive value");
        Preconditions.checkArgument(newMaxParticipants>0, "Number of maximum participants must be a positive value");
        checkIfEventExists(id);
        Event eventToUpdate = findOne(id);
        eventToUpdate.setMinParticipants(newMinParticipants);
        eventToUpdate.setMaxParticipants(newMaxParticipants);
        return eventToUpdate;
    }

    private void checkIfEventExists(UUID id) {
        if (!repository.exists(id)) {
            throw new EventNotFoundException(String.format("Could not find Event with id: %s", id));
        }
    }

}
