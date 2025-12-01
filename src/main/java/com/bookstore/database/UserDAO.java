package com.bookstore.database;

import com.bookstore.exception.DatabaseConnectionException;
import com.bookstore.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT id, username, role FROM users";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role")
                );
                users.add(user);
            }

        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error loading users", e);
        }

        return users;
    }

    public User getById(int id) {
        String sql = "SELECT id, username, role FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("role")
                    );
                }
            }

        } catch (SQLException | DatabaseConnectionException e) {
            throw new RuntimeException("Error loading user id " + id, e);
        }

        return null;
    }
}
