package com.zpi2016.rating.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.zpi2016.rating.domain.EventRating;

/**
 * Created by kuba on 28.02.16.
 */
public interface EventRatingRepository extends CrudRepository<EventRating, UUID> {
}
