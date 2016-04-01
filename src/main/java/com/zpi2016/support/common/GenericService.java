package com.zpi2016.support.common;

import com.zpi2016.event.utils.EventNotFoundException;

import java.util.UUID;

/**
 * Created by aman on 13.03.16.
 */
public interface GenericService<T> {

    T save(final T entity) throws EventNotFoundException;

    T findOne(final UUID id);

    Iterable<T> findAll();

    T update(final T entity, final UUID id) throws EventNotFoundException;

    void delete(final UUID id) throws EventNotFoundException;

}
