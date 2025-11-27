package bookstore.exception;

// Leverage a dedicated custom exception package
public class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(String message) {
        super(message);
    }
}