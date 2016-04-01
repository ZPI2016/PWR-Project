package com.zpi2016.event.service;

import com.zpi2016.event.domain.Event;
import com.zpi2016.event.repository.EventRepository;
import com.zpi2016.event.utils.EventNotFoundException;
import com.zpi2016.location.domain.Location;
import com.zpi2016.support.common.GenericService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.UUID;


/**
 * Created by Sandra on 2016-04-01.
 */
public class EventService implements GenericService<Event> {


    @Autowired
    private EventRepository repository;


    //todo: think about constraints when it comes to event creation (same name and creator as uniqe guarantee)
    @Override
    @Transactional
    public Event save(final Event event) throws EventNotFoundException {
        if (!eventWithIdExists(event.getId()))
            return repository.save(event);
        else
            return null;
    }

    @Override
    public Event findOne(final UUID id) {
        return repository.findOne(id);
    }

    @Override
    public Iterable<Event> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Event update(Event Event, UUID id) throws EventNotFoundException {
        checkIfEventExists(id);
        Event existing = findOne(id);
        existing.copy(Event);
        return existing;
    }

    @Override
    @Transactional
    public void delete(UUID id) throws EventNotFoundException {
        checkIfEventExists(id);
        repository.delete(id);
    }

    @Transactional
    public Location findAddress(UUID id) throws EventNotFoundException {
        checkIfEventExists(id);
        return findOne(id).getPlace();
    }

    @Transactional
    public Location updateAddress(Location newAddress, UUID id) throws EventNotFoundException {
        checkIfEventExists(id);
        Location currentAddress = findOne(id).getPlace();
        currentAddress.copy(newAddress);
        return currentAddress;
    }

    private void checkIfEventExists(UUID id) throws EventNotFoundException {
        if (!repository.exists(id)) {
            throw new EventNotFoundException(String.format("Could not find Event with id: %s", id));
        }
    }

    private boolean eventWithIdExists(UUID id) throws EventNotFoundException {
        return repository.exists(id);
    }


}
