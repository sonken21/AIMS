package com.aims.son.controller.repositories;

import com.aims.son.entity.media.Book;

import java.sql.SQLException;
import java.util.List;

public interface IBookRepository extends IMediaRepository {
    Book findBookById(int id) throws SQLException;
    List<Book> findAllBooks() throws SQLException;
    void saveBook(Book book) throws SQLException;
    void deleteBook(int id) throws SQLException;
}
