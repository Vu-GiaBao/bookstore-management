package com.bookstore.service;

import com.bookstore.database.BookDAO;
import com.bookstore.model.Book;

import java.util.ArrayList;

public class BookService {
    private final BookDAO bookDAO = new BookDAO();


    public void addBook(Book b) {
        if (b == null) {
            System.out.println("Error! Book is null.");
            return;
        }
        int result = bookDAO.insert(b);
        if (result > 0) {
            System.out.println("Added successfully!");
        } else {
            System.out.println("Add failed!");
        } 
    }


    public void displayBooks() {
        ArrayList<Book> books = bookDAO.getAll(); 
        if (books.isEmpty()) {
            System.out.println("No book available!");
            return;
        }
        System.out.println("\nBook List:");
        for (Book b : books) {  
            b.displayInfo();             
        }
    }


    public Book searchBookById(int id) {
        Book b = bookDAO.findByID(id);  
        if (b != null) {
            System.out.println("Book with id: " + id + " is: " + b);
        } else {
            System.out.println("Book not found!");
        }
        return b;
    }


    public ArrayList<Book> searchBookbyTitle(String title) {
        ArrayList<Book> results = bookDAO.findbyTitle(title); 
        if (results.isEmpty()) {
            System.out.println("Book not found!");
        } else {
            System.out.println("Book with title: " + title);
            for (Book b : results) {
                b.displayInfo();
            }
        }
        return results;
    }


    public ArrayList<Book> searchBookbyAuthor(String author) {
        ArrayList<Book> results = bookDAO.findbyAuthor(author);

        if (results.isEmpty()) {
            System.out.println("Book not found!");
        } else {
            System.out.println("Books with author: " + author);
            for (Book b : results) {
                b.displayInfo();
            }
        }
        return results;
    }


    public void updateBook(int id, double newPrice, int newQuantity) {
        Book b = bookDAO.findByID(id); 
        if (b == null) {
            System.out.println("Book not found!");
            return;
        }
        b.setPrice(newPrice);
        b.setQuantity(newQuantity);
        int updated = bookDAO.update(b); 
        if (updated > 0) {
            System.out.println("Book is updated successfully!");
            b.displayInfo();
        } else {
            System.out.println("Update failed!");
        }
    }


    public boolean deleteBookById(int id) {
        int deleted = bookDAO.delete(id);
        if (deleted > 0) {
            System.out.println("Delete successfully!");
            return true;
        } else {
            System.out.println("Delete failed / Book not found!");
            return false;
        }
    }


    public int reduceStockTx(int bookId, int quantityToReduce, Connection conn) throws SQLException {
        if (quantityToReduce <= 0) {
            return 0;
        }
        return bookDAO.reduceStock(bookId, quantityToReduce, conn);
    }   
}