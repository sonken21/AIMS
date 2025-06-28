package com.aims.son.repositories;

import com.aims.son.controller.repositories.IBookRepository;
import com.aims.son.entity.media.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookRepository extends MediaRepository implements IBookRepository {
    public BookRepository() throws SQLException {
    }

    @Override
    public Book findBookById(int id) throws SQLException {
        String sql = "SELECT * FROM Book " +
                "INNER JOIN Media ON Media.id = Book.id " +
                "WHERE Media.id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return mapResultSetToBook(res);
            }
        }
        return null;
    }

    @Override
    public List<Book> findAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Book INNER JOIN Media ON Media.id = Book.id";
        try (Statement stm = connection.createStatement()) {
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                books.add(mapResultSetToBook(res));
            }
        }
        return books;
    }

    @Override
    public void saveBook(Book book) throws SQLException {
        super.saveMedia(book);
        String sql = "INSERT INTO Book (id, author, coverType, publisher, publishDate, numOfPages, language, bookCategory) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, book.getId());
            stm.setString(2, book.getAuthor());
            stm.setString(3, book.getCoverType());
            stm.setString(4, book.getPublisher());
            stm.setDate(5, new java.sql.Date(book.getPublishDate().getTime()));
            stm.setInt(6, book.getNumOfPages());
            stm.setString(7, book.getLanguage());
            stm.setString(8, book.getBookCategory());

            stm.executeUpdate();
        }
    }

    @Override
    public void deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM Book WHERE id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        }

        super.deleteMedia(id);
    }

    private Book mapResultSetToBook(ResultSet res) throws SQLException {
        return new Book (
                res.getInt("id"),
                res.getString("title"),
                res.getString("category"),
                res.getInt("value"),
                res.getInt("price"),
                res.getInt("quantity"),
                res.getString("type"),
                res.getString("imageURl"),
                res.getDouble("weight"),
                res.getString("author"),
                res.getString("coverType"),
                res.getString("publisher"),
                res.getDate("publishDate"),
                res.getInt("numOfPages"),
                res.getString("language"),
                res.getString("bookCategory")
        );
    }
}
