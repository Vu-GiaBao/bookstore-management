package com.bookstore.database;

import com.bookstore.exception.DatabaseConnectionException;
import com.bookstore.model.Book;
import com.bookstore.model.InvoiceItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceItemDAO extends abstractGenericDAO<InvoiceItem> {

    private final BookDAO bookDAO = new BookDAO();


    public int[] insertBatch(String invoiceId,
                             List<InvoiceItem> items,
                             Connection conn) throws SQLException {
        String sql = "INSERT INTO invoiceitem (invoice_id, book_id, quantity, price) " +
                     "VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (InvoiceItem item : items) {
                pstmt.setString(1, invoiceId);
                pstmt.setInt(2, item.getBook().getId());
                pstmt.setInt(3, item.getQuantity());
                pstmt.setDouble(4, item.getBook().getPrice());
                pstmt.addBatch();
            }
            return pstmt.executeBatch();
        }
    }

    // ===== Dùng khi tự tạo Connection bên trong DAO =====
    public int[] insertBatch(String invoiceId, List<InvoiceItem> items) {
        try (Connection conn = DBConnection.getConnection()) {
            return insertBatch(invoiceId, items, conn);
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException(
                    "Error inserting invoice items for invoice id: " + invoiceId, e);
        }
    }

    public List<InvoiceItem> findByInvoiceId(String invoiceId) {
        List<InvoiceItem> items = new ArrayList<>();
        String sql = "SELECT * FROM invoiceitem WHERE invoice_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, invoiceId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int bookId = rs.getInt("book_id");
                    int quantity = rs.getInt("quantity");

                    Book book = bookDAO.findById(bookId);
                    if (book != null) {
                        items.add(new InvoiceItem(book, quantity));
                    }
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException(
                    "Error getting items for invoice id: " + invoiceId, e);
        }

        return items;
    }

     public List<InvoiceItem> getByInvoiceId(String invoiceId) {
        List<InvoiceItem> result = new ArrayList<>();
        String sql = "SELECT * FROM invoiceitem WHERE invoice_id = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, invoiceId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                int qty = rs.getInt("quantity");
                double price = rs.getDouble("price"); 

                Book book = bookDAO.findById(bookId);

                // Dùng constructor đầy đủ
                result.add(new InvoiceItem(book, qty, price));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
