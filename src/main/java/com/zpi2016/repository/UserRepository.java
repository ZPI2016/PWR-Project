package com.zpi2016.repository;

import com.zpi2016.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kuba on 28.02.16.
 */
public interface UserRepository extends CrudRepository <User, Integer> {
}
