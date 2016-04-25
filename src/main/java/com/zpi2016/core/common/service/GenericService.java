package com.zpi2016.core.common.service;

import com.zpi2016.core.common.domain.GenericEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

/**
 * Created by aman on 13.03.16.
 */
public interface GenericService<T> {

    T save(final T entity);

    T findOne(final UUID id);

    Iterable<T> findAll();

    T update(final T entity, final UUID id);

    void delete(final UUID id);

    default GenericEntity getCurrentLoggedUser() {
        return (GenericEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    default Boolean isAuthorized(UUID id) {
        return getCurrentLoggedUser().getId().equals(id);
    }

}
