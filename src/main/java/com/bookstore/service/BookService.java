package com.bookstore.service;

import com.bookstore.model.Book;

import java.util.*;
import java.util.ArrayList;
import java.io.*;

public class BookService {
    private ArrayList<Book> books = new ArrayList<>();


    public void addBooks(Book b) {
        books.add(b);
        System.out.println("Book added!");
    }


    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No book available!");
        }
        System.out.println("\nBook List: ");
        for (Book b: books) {
            b.displayInfo();
        }
    }


    public Book searchBookbyId(int id) {
        for (Book b: books) {
            if (b.getId() == id) {
                System.out.print("Book with id: " + id + " is ");
                return b;
            }
        }
        System.out.println("Book not found!");
        return null;
    }


    public Book displayAll() {
        for (Book b: books) {
            System.out.prinln(b);
        }
    }


    public ArrayList<Book> searchBookbyTitle(String title) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book b: books) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(b);
            }
        }
        if (results.isEmpty()) {
            System.out.println("Book not found!");
        } else {
            System.out.println("Book with title: " + title + " is ");
            for (Book b: results) {
                b.displayInfo();
            }
        }
        return results;
    }


    public ArrayList<Book> searchBookbyAuthor(String author) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book b: books) {
            if (b.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(b);
            }
        }
        if (results.isEmpty()) {
            System.out.println("Book not found!");
        } else {
            System.out.println("Book with author: " + author + " is ");
            for (Book b: results) {
                b.displayInfo();
            }
        }
        return results;
    }


    public Book updateBook(int id, double newPrice, int newQuantity) {
        Book b = searchBookbyId(id);
        if (b != null) {
            if (newPrice > 0.0) b.setPrice(newPrice);
            if (newQuantity > 0) b.setQuantity(newQuantity);
            return b;
        }
        return null;
    }


    public void removeBook(int id) {
        books.removeIf(b -> b.getId() == id);
        System.out.println("Book with id: " + id + " is removed successfully!");
    }


    public void displayBook() {
        for (Book b: books) {
            System.out.println(b);
        }
    }
}