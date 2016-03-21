package com.zpi2016.service;

import java.util.List;

/**
 * Created by aman on 13.03.16.
 */
public interface GenericService<T> {

    T save(final T entity);

    T findOne(final Integer id);

    Iterable<T> findAll();

    T update(T entity, Integer id);

    void delete(final T entity);

    boolean exists(final Integer id);

    Long count();

}
