package com.epamcourse.homework8.model.dao;

import com.epamcourse.homework8.model.entity.Book;
import com.epamcourse.homework8.exception.DaoException;
import java.util.List;

public interface BookListDao extends Dao<Book,Integer>{
    List<Book> readByName(String name) throws DaoException;

    List<Book> readByAuthor(String[] author) throws DaoException;

    List<Book> readHavingNumberOfPageLessThan(int maxNumberOfPage) throws DaoException;

    List<Book> sortByNumberOfPage() throws DaoException;

    List<Book> sortByName() throws DaoException;
}