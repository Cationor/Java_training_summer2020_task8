package com.epamcourse.homework8.model.dao.impl;

import com.epamcourse.homework8.model.dao.BookListDao;
import com.epamcourse.homework8.model.connection.ConnectionPool;
import com.epamcourse.homework8.model.entity.Book;
import com.epamcourse.homework8.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookListListDaoImpl implements BookListDao {
    private static final String RELATION_BOOK_ATTRIBUTE_BOOK_ID = "bookid";
    private static final String RELATION_BOOK_ATTRIBUTE_NAME = "name";
    private static final String RELATION_BOOK_ATTRIBUTE_NUMBER_OF_PAGE = "numberofpage";
    private static final String RELATION_BOOK_ATTRIBUTE_AUTHOR = "author";
    private static final int PREPARED_STATEMENT_FIRST_INDEX = 1;
    private static final int PREPARED_STATEMENT_FIRST_SECOND = 2;
    private static final int PREPARED_STATEMENT_FIRST_THIRD = 3;
    private static final int PREPARED_STATEMENT_FIRST_FOURTH = 4;
    private static final String SQL_CREATE_BOOK = "insert into book(name, numberofpage, author) values(?,?,?)";
    private static final String SQL_READ_BOOK_BY_ID = "select name, numberofpage, author from book where bookid = ?";
    private static final String SQL_READ_BOOK_BY_NAME = "select bookid, numberofpage, author from book where name = ?";
    private static final String SQL_READ_BOOK_BY_AUTHOR =
            "select bookid, name, numberofpage from book where author = ?";
    private static final String SQL_READ_BOOK_HAVING_PAGES_LESS_THAN =
            "select bookid, name, numberofpage, author from book where numberofpage < ?";
    private static final String SQL_UPDATE_BOOK = "update book set name=?, numberofpage=?, author=? where bookid = ?";
    private static final String SQL_DELETE_BOOK = "delete from book where bookid = ?";
    private static final String SQL_SORT_BY_NUMBER_OF_PAGE =
            "select bookid, name, numberofpage, author from book order by numberofpage";
    private static final String SQL_SORT_BY_NAME =
            "select bookid, name, numberofpage, author from book order by name";
    private static final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Book book) throws DaoException {
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_BOOK)) {
            statement.setString(PREPARED_STATEMENT_FIRST_INDEX, book.getName());
            statement.setInt(PREPARED_STATEMENT_FIRST_SECOND, book.getNumberOfPage());
            statement.setString(PREPARED_STATEMENT_FIRST_THIRD, Arrays.toString(book.getAuthor()));
            statement.execute();
        } catch (SQLException throwables) {
            throw new DaoException();
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public Book read(Integer id) throws DaoException {
        Book book;
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_READ_BOOK_BY_ID)) {
            statement.setInt(PREPARED_STATEMENT_FIRST_INDEX, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                book = new Book(id, resultSet.getString(RELATION_BOOK_ATTRIBUTE_NAME),
                        resultSet.getInt(RELATION_BOOK_ATTRIBUTE_NUMBER_OF_PAGE),
                        resultSet.getString(RELATION_BOOK_ATTRIBUTE_AUTHOR).replace("[", "")
                                .replace("]", "").split(", "));
            }
        } catch (SQLException throwables) {
            throw new DaoException();
        } finally {
            pool.releaseConnection(connection);
        }
        return book;
    }

    @Override
    public List<Book> readByName(String name) throws DaoException {
        List<Book> book = new ArrayList<>();
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_READ_BOOK_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    book.add(new Book(resultSet.getInt(RELATION_BOOK_ATTRIBUTE_BOOK_ID), name,
                            resultSet.getInt(RELATION_BOOK_ATTRIBUTE_NUMBER_OF_PAGE),
                            resultSet.getString(RELATION_BOOK_ATTRIBUTE_AUTHOR).replace("[", "")
                                    .replace("]", "").split(", ")));
                }
            }
        } catch (SQLException throwables) {
            throw new DaoException();
        } finally {
            pool.releaseConnection(connection);
        }
        return book;
    }

    @Override
    public List<Book> readByAuthor(String[] author) throws DaoException {
        List<Book> book = new ArrayList<>();
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_READ_BOOK_BY_AUTHOR)) {
            statement.setString(1, Arrays.toString(author));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    book.add(new Book(resultSet.getInt(RELATION_BOOK_ATTRIBUTE_BOOK_ID),
                            resultSet.getString(RELATION_BOOK_ATTRIBUTE_NAME),
                            resultSet.getInt(RELATION_BOOK_ATTRIBUTE_NUMBER_OF_PAGE),
                            author));
                }
            }
        } catch (SQLException throwables) {
            throw new DaoException();
        } finally {
            pool.releaseConnection(connection);
        }
        return book;
    }

    @Override
    public List<Book> readHavingNumberOfPageLessThan(int maxNumberOfPage) throws DaoException {
        List<Book> book = new ArrayList<>();
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_READ_BOOK_HAVING_PAGES_LESS_THAN)) {
            statement.setInt(1, maxNumberOfPage);
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    book.add(new Book(resultSet.getInt(RELATION_BOOK_ATTRIBUTE_BOOK_ID),
                            resultSet.getString(RELATION_BOOK_ATTRIBUTE_NAME),
                            resultSet.getInt(RELATION_BOOK_ATTRIBUTE_NUMBER_OF_PAGE),
                            resultSet.getString(RELATION_BOOK_ATTRIBUTE_AUTHOR).replace("[", "")
                                    .replace("]", "").split(", ")));
                }
            }
        } catch (SQLException throwables) {
            throw new DaoException();
        } finally {
            pool.releaseConnection(connection);
        }
        return book;
    }

    @Override
    public void update(Book book) throws DaoException {
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BOOK)) {
            statement.setString(PREPARED_STATEMENT_FIRST_INDEX, book.getName());
            statement.setInt(PREPARED_STATEMENT_FIRST_SECOND, book.getNumberOfPage());
            statement.setString(PREPARED_STATEMENT_FIRST_THIRD, Arrays.toString(book.getAuthor()));
            statement.setInt(PREPARED_STATEMENT_FIRST_FOURTH, book.getBookId());
            statement.execute();
        } catch (SQLException throwables) {
            throw new DaoException();
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void delete(Book book) throws DaoException {
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BOOK)) {
            statement.setInt(PREPARED_STATEMENT_FIRST_INDEX, book.getBookId());
            statement.execute();
        } catch (SQLException throwables) {
            throw new DaoException();
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<Book> sortByNumberOfPage() throws DaoException {
        return readBookListAdheredToSqlQuery(SQL_SORT_BY_NUMBER_OF_PAGE);
    }

    @Override
    public List<Book> sortByName() throws DaoException {
        return readBookListAdheredToSqlQuery(SQL_SORT_BY_NAME);
    }

    private List<Book> readBookListAdheredToSqlQuery(String sqlSortQuery) throws DaoException {
        List<Book> book = new ArrayList<>();
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sqlSortQuery);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                book.add(new Book(resultSet.getInt(RELATION_BOOK_ATTRIBUTE_BOOK_ID),
                        resultSet.getString(RELATION_BOOK_ATTRIBUTE_NAME),
                        resultSet.getInt(RELATION_BOOK_ATTRIBUTE_NUMBER_OF_PAGE),
                        resultSet.getString(RELATION_BOOK_ATTRIBUTE_AUTHOR).replace("[", "")
                                .replace("]", "").split(", ")));
            }
        } catch (SQLException throwables) {
            throw new DaoException();
        } finally {
            pool.releaseConnection(connection);
        }
        return book;
    }
}
