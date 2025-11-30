package com.bookstore;

import com.bookstore.database.DBConnection;
import java.sql.*;
import com.bookstore.exception.DatabaseConnectionException;

public class TestDB {
    public static void main(String[] args) {
        
        try{
            Connection conn = DBConnection.getConnection();
            System.out.println(conn);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM books");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("title"));
            }
        }
        catch(DatabaseConnectionException e)
        {
            System.err.println("Database connection failed during testing");
            e.printStackTrace();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
