package com.zpi2016.repository;

import com.zpi2016.model.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by kuba on 28.02.16.
 */
public interface EventRepository extends CrudRepository<Event, Integer> {

    public List<Event> findByStartTimeBefore(Date date);

    public List<Event> findByStartTimeAfter(Date date);

    public List<Event> findByStartTimeBetween(Date start, Date end);

}
