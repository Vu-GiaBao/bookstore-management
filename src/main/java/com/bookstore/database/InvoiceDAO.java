package com.bookstore.database;

import com.bookstore.exception.DatabaseConnectionException;
import com.bookstore.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    public int insert(Invoice invoice, Connection conn) throws SQLException {
        String sql = "INSERT INTO invoice (invoice_code, customer_id, user_id, invoice_date, total_amount, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, invoice.getInvoiceId());
        pstmt.setInt(2, invoice.getCustomerId());
        pstmt.setInt(3, invoice.getUserId());
        pstmt.setDate(4, Date.valueOf(invoice.getDate()));
        pstmt.setDouble(5, invoice.getTotalAmount());
        pstmt.setString(6, invoice.getStatus().name());

        return pstmt.executeUpdate();
    }

    public int insertItem(InvoiceItem item, String invoiceId, Connection conn) throws SQLException {
        String sql = "INSERT INTO invoice_item (invoice_code, book_id, quantity, price) VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, invoiceId);
        pstmt.setInt(2, item.getBook().getId());
        pstmt.setInt(3, item.getQuantity());
        pstmt.setDouble(4, item.getBook().getPrice());

        return pstmt.executeUpdate();
    }
    public void delete(String id) {
    String sql = "DELETE FROM invoice WHERE id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, id);
        pstmt.executeUpdate();

    } catch (SQLException | DatabaseConnectionException e) {
        throw new RuntimeException("Error deleting invoice: " + id, e);
    }
}
public List<Invoice> getAll() {
    List<Invoice> invoices = new ArrayList<>();
    String sql = "SELECT * FROM invoice";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            invoices.add(new Invoice(
                    rs.getString("id"),
                    rs.getDate("invoice_date").toLocalDate(),
                    rs.getDouble("total_amount"),
                    InvoiceStatus.valueOf(rs.getString("status")),
                    rs.getInt("customer_id"),
                    rs.getInt("user_id")
            ));
        }

    } catch (SQLException | DatabaseConnectionException e) {
        throw new RuntimeException("Error getting all invoices", e);
    }

    return invoices;
}
public int insert(Invoice invoice) {
    String sql = "INSERT INTO invoice(customer_id, user_id, invoice_date, total_amount, status) "
               + "VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, invoice.getCustomerId());
        pstmt.setInt(2, invoice.getUserId());
        pstmt.setDate(3, Date.valueOf(invoice.getDate()));
        pstmt.setDouble(4, invoice.getTotalAmount());
        pstmt.setString(5, invoice.getStatus().name());

        return pstmt.executeUpdate();

    } catch (SQLException | DatabaseConnectionException e) {
        throw new RuntimeException("Error inserting invoice", e);
    }
}

}
