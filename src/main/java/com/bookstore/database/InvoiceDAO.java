package com.bookstore.database;

import com.bookstore.exception.DatabaseConnectionException;
import com.bookstore.model.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class InvoiceDAO extends abstractGenericDAO<Invoice> {
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
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT invoice_id, customer_id, user_id, invoice_date, total_amount, status FROM invoice";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {

                String invoiceId = rs.getString("invoice_id");
                int customerId = rs.getInt("customer_id");
                int userId = rs.getInt("user_id");
                LocalDate date = rs.getDate("invoice_date").toLocalDate();
                double total = rs.getDouble("total_amount");
                String statusStr = rs.getString("status");

                InvoiceStatus status = InvoiceStatus.valueOf(statusStr);

                Invoice inv = new Invoice(invoiceId, date, total, status, customerId, userId);
                list.add(inv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    return list;
}



    public int insert(Invoice invoice) {
        String sql = "INSERT INTO invoice(invoice_id, customer_id, user_id, invoice_date, total_amount, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, invoice.getInvoiceId());
            pstm.setInt(2, invoice.getCustomerId());
            pstm.setInt(3, invoice.getUserId());
            pstm.setDate(4, Date.valueOf(invoice.getDate()));
            pstm.setDouble(5, invoice.getTotalAmount());
            pstm.setString(6, invoice.getStatus().name());

            return pstm.executeUpdate();

        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error inserting invoice", e);
        }
    }

    public void updateStatus(String invoiceId, InvoiceStatus status) {
        String sql = "UPDATE invoice SET status = ? WHERE invoice_id = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            stmt.setString(2, invoiceId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int countInvoices() {
        String sql = "SELECT COUNT(*) FROM invoice";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
