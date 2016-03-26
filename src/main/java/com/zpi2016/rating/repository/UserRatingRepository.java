package com.zpi2016.rating.repository;

import com.zpi2016.rating.domain.UserRating;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Created by kuba on 28.02.16.
 */
public interface UserRatingRepository extends CrudRepository<UserRating, UUID> {
}
