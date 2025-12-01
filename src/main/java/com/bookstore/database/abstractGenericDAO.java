package com.bookstore.database;

import java.sql.Connection;
import java.sql.SQLException;


public abstract class abstractGenericDAO<T> {

    protected DBConnection dbconnection = new DBConnection(); 


    protected String SQLExceptionHandler(SQLException e) {
        String errorMessage = "SQL Error Code: " + e.getErrorCode() + ". State: " + e.getSQLState() + ". ";
        switch (e.getErrorCode()) {
            case 1062:
                errorMessage += "Duplicate entry.";
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
    

    protected void closeConnection(Connection connection) {
        dbconnection.closeConnection(connection);
    }
}