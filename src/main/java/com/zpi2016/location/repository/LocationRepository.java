package com.zpi2016.location.repository;

import com.zpi2016.location.domain.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Created by kuba on 28.02.16.
 */
public interface LocationRepository extends CrudRepository<Location, UUID> {
}
