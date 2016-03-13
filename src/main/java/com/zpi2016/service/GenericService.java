package com.zpi2016.service;

import java.util.List;

/**
 * Created by aman on 13.03.16.
 */
public interface GenericService<T> {

    T save(final T enitity);

    T findOne(int ID);

    Iterable<T> findAll();

    Long count();

    void delete(final T entity);

    boolean exists(int ID);

}
