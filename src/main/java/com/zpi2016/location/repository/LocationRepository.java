package com.zpi2016.location.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.zpi2016.location.domain.Location;

/**
 * Created by kuba on 28.02.16.
 */
public interface LocationRepository extends CrudRepository<Location, UUID> {
}
