package com.bookstore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.bookstore.exception.DatabaseConnectionException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/bookstore";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static Connection getConnection() throws DatabaseConnectionException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Database connected successfully.");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseConnectionException("Failed to connect to the database", e);
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
