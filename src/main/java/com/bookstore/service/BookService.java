package com.bookstore.service;

import com.bookstore.database.BookDAO;
import com.bookstore.database.DBConnection;
import com.bookstore.model.Book;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookService {
    private final BookDAO bookDAO = new BookDAO();

    public int addBook(Book book) {
        if (book == null) {
            return 0;
        }
        return bookDAO.insert(book);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAll();
    }

    public Book getBookById(int id) {
        return bookDAO.findById(id);
    }

    public List<Book> getBooksByTitle(String title) {
        return bookDAO.findByTitle(title);
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookDAO.findByAuthor(author);
    }

    public boolean updateBook(Book book) {
        if (book == null) {
            return false;
        }
        return bookDAO.update(book) > 0;
    }

    public boolean deleteBook(int id) {
        return bookDAO.delete(id) > 0;
    }

    public int getTotalBooks() {
        return bookDAO.countBooks();
    }

    public boolean reduceStock(int bookId, int quantityToReduce) {
        if (quantityToReduce <= 0) {
            return false;
        }
        try {
            return bookDAO.reduceStock(bookId, quantityToReduce) > 0;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void increaseStock(int bookId, int quantityToAdd) {
        try (Connection conn = DBConnection.getConnection()) {
            new BookDAO().increaseStock(bookId, quantityToAdd, conn);
        } catch (Exception e) {
            throw new RuntimeException("Error increasing stock for book id: " + bookId, e);
        }
}


    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllBooks();
        }
        String lowerCaseKeyword = keyword.toLowerCase();
        List<Book> booksByTitle = bookDAO.findByTitle(lowerCaseKeyword);
        List<Book> booksByAuthor = bookDAO.findByAuthor(lowerCaseKeyword);

        return Stream.concat(booksByTitle.stream(), booksByAuthor.stream())
                .distinct()
                .collect(Collectors.toList());
    }
}