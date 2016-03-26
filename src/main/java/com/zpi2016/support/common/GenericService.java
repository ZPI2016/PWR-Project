package com.zpi2016.support.common;

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

}
