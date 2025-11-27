package bookstore.database;

import bookstore.exception.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // TODO: Configure Database Connection. Update the URL, username, and password below.
    private static final String URL = "jdbc:mysql://localhost:3306/bookstore_db";
    private static final String USER = "root";
    private static final String PASS = "your_password"; // TODO: Security Configuration. Replace this placeholder with the actual password.

    public Connection connectDatabase() throws DatabaseConnectionException {
        Connection connection = null;
        try {
            // Register the Driver (Not strictly necessary with JDBC 4.0+, but good practice)
            // Class.forName("com.mysql.cj.jdbc.Driver"); 
            connection = DriverManager.getConnection(URL, USER, PASS);
            if (connection == null) {
                throw new DatabaseConnectionException("Connection is null, driver connection failed.");
            }
            return connection;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("SQL Connection Failed: " + e.getMessage());
        }
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}