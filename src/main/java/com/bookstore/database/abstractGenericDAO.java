package com.bookstore.database;

import java.sql.Connection;
import java.sql.SQLException;

// Abstract Base DAO Class: Provides generic, shared CRUD methods for all concrete Data Access Objects.
public abstract class abstractGenericDAO<T> {

    // This Connection object will be utilized by all subsequent Data Access Objects (DAOs).
    protected DBConnection dbconnection = new DBConnection(); 

    // Handles generic SQLException instances.
    protected String SQLExceptionHandler(SQLException e) {
        String errorMessage = "SQL Error Code: " + e.getErrorCode() + ". State: " + e.getSQLState() + ". ";
        switch (e.getErrorCode()) {
            case 1062:
                errorMessage += "Duplicate entry. (Có thể trùng ID hoặc UNIQUE key)";
                break;
            case 1452:
                errorMessage += "Cannot add or update a child row: a foreign key constraint fails. (Khóa ngoại không hợp lệ)";
                break;
            case 1146:
                errorMessage += "Table not found. (Không tìm thấy bảng)";
                break;
            default:
                errorMessage += "Message: " + e.getMessage();
                break;
        }
        return errorMessage;
    }
    
    // Closes/Releases the database connection resource.
    protected void closeConnection(Connection connection) {
        dbconnection.closeConnection(connection);
    }
}