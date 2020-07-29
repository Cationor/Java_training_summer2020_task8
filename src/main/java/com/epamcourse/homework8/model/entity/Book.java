package com.epamcourse.homework8.model.entity;

import java.util.Arrays;

public class Book {
    private int bookId;
    private String name;
    private int numberOfPage;
    private String[] author;

    public Book(String name, int numberOfPage, String... author) {
        this.name = name;
        this.numberOfPage = numberOfPage;
        this.author = author;
    }

    public Book(int bookId, String name, int numberOfPage, String... author) {
        this.bookId = bookId;
        this.name = name;
        this.numberOfPage = numberOfPage;
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }

    public boolean equalsIgnoreBookId(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        if (numberOfPage != book.numberOfPage || !name.equals(book.name)) {
            return false;
        }
        return Arrays.equals(author, book.author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        if (bookId != book.bookId || numberOfPage != book.numberOfPage || !name.equals(book.name)) {
            return false;
        }
        return Arrays.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        int result = bookId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + numberOfPage;
        result = 31 * result + Arrays.hashCode(author);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder("id=").append(bookId).append("|name=").append(name).append("|numberOfPage=")
                .append(numberOfPage)
                .append("|author")
                .append(Arrays.toString(author))
                .toString();
    }
}
