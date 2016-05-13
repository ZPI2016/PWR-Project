package com.zpi2016.event.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.zpi2016.event.domain.Category;
import com.zpi2016.event.domain.Event;
import com.zpi2016.location.domain.Location;
import com.zpi2016.user.domain.User;

/**
 * Created by kuba on 28.02.16.
 */
public interface EventRepository extends CrudRepository<Event, UUID> {

    public List<Event> findByCategory(Category category);

    public List<Event> findByStartTime(Date startTime);

    public List<Event> findByStartTimeBefore(Date beforeDate);

    public List<Event> findByStartTimeAfter(Date afterDate);

    public List<Event> findByStartTimeBetween(Date start, Date end);

    public List<Event> findByCreator(User creator);

    public List<Event> findByPlace(Location location);

    public List<Event> findByDeadline(Date deadline);

    public List<Event> findByDeadlineBefore(Date deadlineBefore);

    public List<Event> findByDeadlineAfter(Date deadlineAfter);

    public List<Event> findByDeadlineBetween(Date start, Date end);

    public List<Event> findByMinParticipants(Integer minParticipants);

    public List<Event> findByMaxParticipants(Integer maxParticipants);

    @Query("select e from Event e where e.minParticipants >= ?1 and e.maxParticipants <= ?2")
    public List<Event> findByParticipantsBetween(Integer minParticipants, Integer maxParticipants);

}
