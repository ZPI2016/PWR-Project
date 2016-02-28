package com.zpi2016.repository;

import com.zpi2016.model.UserRating;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kuba on 28.02.16.
 */
public interface UserRatingRepository extends CrudRepository<UserRating, Integer> {
}
