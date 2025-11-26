package com.bookstore.service;

import com.bookstore.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookService {

    // SINGLETON INSTANCE
    private static final BookService instance = new BookService();
    public static BookService getInstance() {
        return instance;
    }

    // BOOK STORAGE
    private final ObservableList<Book> books = FXCollections.observableArrayList();

    // PRIVATE CONSTRUCTOR
    private BookService() {
        books.add(new Book(1, "Harry Potter", "J.K. Rowling", 19.99));
        books.add(new Book(2, "Atomic Habits", "James Clear", 14.99));
        books.add(new Book(3, "Sherlock Holmes", "Arthur Conan Doyle", 12.50));
    }

    // RETURN LIST
    public ObservableList<Book> getBooks() {
        return books;
    }

    // CRUD
    public void addBook(Book book) {
        books.add(book);
    }

    public void deleteBook(Book book) {
        books.remove(book);
    }

    public int generateId() {
        return books.size() + 1;
    }

    public ObservableList<Book> search(String keyword) {
        ObservableList<Book> result = FXCollections.observableArrayList();

        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(b);
            }
        }

        return result;
    }
}
