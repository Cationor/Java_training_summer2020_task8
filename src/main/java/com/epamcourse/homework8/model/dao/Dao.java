package com.epamcourse.homework8.model.dao;

import com.epamcourse.homework8.exception.DaoException;

public interface Dao <T, E>{
    void create(T item)throws DaoException;
    T read(E tag) throws DaoException;
    void update(T item) throws DaoException;
    void delete(T item) throws DaoException;
}
