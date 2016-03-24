package com.zpi2016.service;

import java.util.List;

/**
 * Created by aman on 13.03.16.
 */
public interface GenericService<T> {

    T save(final T entity);

    T findOne(final String id);

    Iterable<T> findAll();

    T update(final T entity, final String id);

    void delete(final T entity);

    void delete(final String id);

    boolean exists(final String id);

    Long count();
}
