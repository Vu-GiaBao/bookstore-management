package com.bookstore.database;

import com.bookstore.model.Customer;
import com.bookstore.exception.DatabaseConnectionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public Customer findById(int id) {
        String sql = "SELECT * FROM Customers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address") 
                    );
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error finding customer by id: " + id, e);
        }
        return null;
    }

    public List<Customer> findByName(String name) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers WHERE name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    customers.add(new Customer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address")
                    ));
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error finding customers by name: " + name, e);
        }
        return customers;
    }

    public Customer findByEmail(String email) {
        String sql = "SELECT * FROM Customers WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address")
                    );
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error finding customer by email: " + email, e);
        }
        return null;
    }

    public Customer findByPhone(String phone) {
        String sql = "SELECT * FROM Customers WHERE phone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phone);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address")
                    );
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error finding customer by phone: " + phone, e);
        }
        return null;
    }

    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                ));
            }
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error getting all customers", e);
        }
        return customers;
    }

    public int insert(Customer customer) {
        String sql = "INSERT INTO Customers(id, name, email, phone, address) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customer.getId());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5, customer.getAddress());

            return pstmt.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error inserting customer: " + customer, e);
        }
    }

    public int update(Customer customer) {
        String sql = "UPDATE Customers SET name = ?, email = ?, phone = ?, address = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getAddress()); // address ở vị trí 4
            pstmt.setInt(5, customer.getId());         // id ở vị trí 5

            return pstmt.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error updating customer: " + customer, e);
        }
    }


    public int delete(int id) {
        String sql = "DELETE FROM Customers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error deleting customer by id: " + id, e);
        }
   }
    public int countCustomers() {
        String sql = "SELECT COUNT(*) FROM customers";
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