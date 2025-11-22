package com.bookstore;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;

public class App {
    public static void main(String[] args) {
        BookService service = new BookService();

        // Load existing data if any
        service.loadFromFile("books.txt");

        // Add some books
        service.addBooks(new Book(1, "Java Basics", "James Gosling", 25.5, 10));
        service.addBooks(new Book(2, "Clean Code", "Robert C. Martin", 30.0, 5));

        // Display books
        service.displayBooks();

        // Update book
        service.updateBook(1, 27.5, 8);

        // Remove a book
        service.removeBook(2);

        // Save to file
        service.saveToFile("books.txt");
    }
}
