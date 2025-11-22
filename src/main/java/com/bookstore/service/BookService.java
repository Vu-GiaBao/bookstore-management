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

    public void updateBook(int id, double newPrice, int newQuantity) {
        Book b = searchBookbyId(id);
        if (b != null) {
            b.setPrice(newPrice);
            b.setQuantity(newQuantity);
            System.out.println("Book is updated successfully!");
            b.displayInfo();
        }
    }

    public void removeBook(int id) {
        books.removeIf(b -> b.getId() == id);
        System.out.println("Book with id: " + id + " is removed successfully!");
    }

    public void saveToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Book b: books) {
                bw.write(b.toString());
                bw.newLine();
            }
            System.out.println("Books saved!");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        books.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                books.add(new Book(Integer.parseInt(data[0]),
                                data[1],
                                data[2],
                                Double.parseDouble(data[3]),
                                Integer.parseInt(data[4])
                        ));
            }
            System.out.println("Books loaded!");
        } catch (IOException e) {
            System.out.println("Error loading file: File not found or empty");
        }
    }
}