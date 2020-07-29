package com.epamcourse.homework8.model.service;

import com.epamcourse.homework8.model.dao.impl.BookListListDaoImpl;
import com.epamcourse.homework8.model.entity.Book;
import com.epamcourse.homework8.exception.DaoException;
import com.epamcourse.homework8.exception.ServiceException;

import java.util.List;

public class BookService {
    private BookListListDaoImpl dao = new BookListListDaoImpl();

    public void addBook(Book book) throws ServiceException {
        if (book == null) {
            throw new ServiceException();
        }
        try {
            dao.create(book);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Book findById(int tag) throws ServiceException {
        try {
            return dao.read(tag);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Book> findByName(String tag) throws ServiceException {
        try {
            return dao.readByName(tag);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Book> findByAuthor(String[] tag) throws ServiceException {
        try {
            return dao.readByAuthor(tag);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Book> findByNumberOfPage(int maxNumberOfPage) throws ServiceException {
        try {
            return dao.readHavingNumberOfPageLessThan(maxNumberOfPage);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void removeBook(Book book) throws ServiceException {
        if (book == null) {
            throw new ServiceException();
        }
        try {
            dao.delete(book);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Book> sortByNumberOfPage() throws ServiceException {
        try {
            return dao.sortByNumberOfPage();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Book> sortByName() throws ServiceException {
        try {
            return dao.sortByName();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
