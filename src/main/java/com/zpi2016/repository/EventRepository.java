package com.zpi2016.repository;

import com.zpi2016.model.Category;
import com.zpi2016.model.Event;
import com.zpi2016.model.Location;
import com.zpi2016.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by kuba on 28.02.16.
 */
public interface EventRepository extends CrudRepository<Event, String> {

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
