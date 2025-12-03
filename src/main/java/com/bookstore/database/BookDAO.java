package com.bookstore.database;

import com.bookstore.model.Book;
import com.bookstore.exception.DatabaseConnectionException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends abstractGenericDAO<Book> {
    public Book findById(int id) {
        String sql = "SELECT * FROM Books WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getDouble("price"),
                            rs.getInt("quantity")
                    );
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error finding book by id: " + id, e);
        }
        return null;
    }

    public List<Book> findByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE title LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + title + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getDouble("price"),
                            rs.getInt("quantity")
                    ));
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error finding books by title: " + title, e);
        }
        return books;
    }

    public List<Book> findByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE author LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + author + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getDouble("price"),
                            rs.getInt("quantity")
                    ));
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error finding books by author: " + author, e);
        }
        return books;
    }

    public int insert(Book book) {
        try (Connection conn = DBConnection.getConnection()) {
            return insert(book, conn);
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error inserting book: " + book, e);
        }
    }

    public int insert(Book book, Connection conn) throws SQLException {
        String sql = "INSERT INTO Books(title, author, price, quantity) VALUES (?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setDouble(3, book.getPrice());
            pstmt.setInt(4, book.getQuantity());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
        }
    }

    public int update(Book book) {
        try (Connection conn = DBConnection.getConnection()) {
            return update(book, conn);
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error updating book: " + book, e);
        }
    }

    public int update(Book book, Connection conn) throws SQLException {
        String sql = "UPDATE Books SET title = ?, author = ?, price = ?, quantity = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setDouble(3, book.getPrice());
            pstmt.setInt(4, book.getQuantity());
            pstmt.setInt(5, book.getId());
            return pstmt.executeUpdate();
        }
    }

    public int delete(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            return delete(id, conn);
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error deleting book by id: " + id, e);
        }
    }

    public int delete(int id, Connection conn) throws SQLException {
        String sql = "DELETE FROM Books WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println(e);
            throw new RuntimeException("Error getting all books", e);
        }
        return books;
    }

    public int reduceStock(int bookId, int quantityToReduce) {
        try (Connection conn = DBConnection.getConnection()) {
            return reduceStock(bookId, quantityToReduce, conn);
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error reducing stock for book id: " + bookId, e);
        }
    }

    public int reduceStock(int bookId, int quantityToReduce, Connection conn) throws SQLException {
        String sql = "UPDATE Books SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantityToReduce);
            pstmt.setInt(2, bookId);
            pstmt.setInt(3, quantityToReduce);
            return pstmt.executeUpdate();
        }
    }
    
    public int increaseStock(int bookId, int quantityToAdd, Connection conn) throws SQLException {
        String sql = "UPDATE Books SET quantity = quantity + ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantityToAdd);
            pstmt.setInt(2, bookId);
            return pstmt.executeUpdate();
        }
    }

    public int countBooks() {
        String sql = "SELECT COUNT(*) FROM books";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}