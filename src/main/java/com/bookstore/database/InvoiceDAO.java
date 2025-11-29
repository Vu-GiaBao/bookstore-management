package com.bookstore.database;

import com.bookstore.model.Invoice;
import com.bookstore.model.InvoiceStatus;
import bookstore.exception.DatabaseConnectionException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class InvoiceDAO extends abstractGenericDAO<Invoice> {

    // QUERY BY ID (STRING) - Ensure the Invoice Model is configured with all necessary Foreign Key fields.
    public Invoice findByID(String id){
        Connection connection = null;
        Invoice invoice = null;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "SELECT * FROM Invoice WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // TODO: Skip object creation to avoid 'Missing Constructor' error. Requires review once the constructor is implemented.
                // REQUIRED: Implement Invoice(id, date, totalAmount, status, customerId, userId) constructor.
            }
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return invoice;
    }
    
    public int insert(Invoice entity){
        Connection connection = null;
        int addedRow = 0;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "INSERT INTO Invoice(id, customer_id, user_id, invoice_date, total_amount, status) VALUES (?,?,?,?,?,?)" ;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getInvoiceId());
            // ASSUMPTION: The Invoice entity object provides accessors for the Foreign Keys: getCustomerId() and getUserId().
            // ps.setInt(2, entity.getCustomerId()); 
            // ps.setString(3, entity.getUserId());
            ps.setDate(4, Date.valueOf(entity.getDate())); 
            ps.setDouble(5, entity.getTotalAmount()); 
            ps.setString(6, entity.getStatus().name());
            addedRow = ps.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return addedRow;
    }
    
    public int updateStatus(String invoiceId, InvoiceStatus newStatus){
        Connection connection = null;
        int updatedRow = 0;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "UPDATE Invoice SET status = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newStatus.name());
            ps.setString(2, invoiceId);
            updatedRow = ps.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return updatedRow;
    }
}