package bookstore.database;

import com.bookstore.model.Book;
import bookstore.exception.DatabaseConnectionException;
import java.sql.*;
import java.util.ArrayList;

public class BookDAO extends abstractGenericDAO<Book> {
    
    public Book findByID(int id){
        Connection connection = null;
        Book book = null;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "SELECT * FROM Book WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id); 
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                book = new Book(
                    rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                    rs.getDouble("price"), rs.getInt("quantity")
                );
            }
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return book;
    }

    public ArrayList<Book> findbyTitle(String title) {
        Connection connection = null;
        ArrayList<Book> bookList = new ArrayList<>();
        
        try {
            connection = dbconnection.connectDatabase();
            // SQL Search: Utilize the LIKE operator for fuzzy/partial-match querying on the title field.
            String sql = "SELECT * FROM Book WHERE title LIKE ?"; 
            PreparedStatement ps = connection.prepareStatement(sql);
            
            // Parameter Binding: Concatenate the search term with the SQL wildcard characters for fuzzy matching
            ps.setString(1, "%" + title + "%"); 
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bookList.add(new Book(
                    rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                    rs.getDouble("price"), rs.getInt("quantity")
                ));
            }
        }
        catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        }
        finally {
            closeConnection(connection);
        }
        return bookList;
    }

    public ArrayList<Book> findbyAuthor(String author) {
        Connection connection = null;
        ArrayList<Book> bookList = new ArrayList<>();
        
        try {
            connection = dbconnection.connectDatabase();
            // SQL Search: Utilize the LIKE operator for fuzzy/partial-match querying on the author name field.
            String sql = "SELECT * FROM Book WHERE author LIKE ?"; 
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setString(1, "%" + author + "%"); 
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bookList.add(new Book(
                    rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                    rs.getDouble("price"), rs.getInt("quantity")
                ));
            }
        }
        catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        }
        finally {
            closeConnection(connection);
        }
        return bookList;
    }
    
    // Transaction Support Method: This utility method assists the InvoiceService in managing complex database transactions.
    // CRITICAL: Do not remove. 
    // This method is essential for centralizing transaction control within the Service Layer logic.
    public int reduceStock(int bookId, int quantityToReduce, Connection connection) throws SQLException {
        // BUSINESS LOGIC CONSTRAINT: Ensure the UPDATE logic includes a check: WHERE quantity >= quantityToReduce to prevent inventory running below zero.
        String sql = "UPDATE Book SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, quantityToReduce);
        ps.setInt(2, bookId);
        ps.setInt(3, quantityToReduce); 
        // CRITICAL NOTE: Do not close the PreparedStatement (ps). 
        // The underlying Connection is shared by the active Transaction and must be managed/closed by the calling Service Layer.
        return ps.executeUpdate();
    }

    public int insert(Book entity){
        Connection connection = null;
        int addedRow = 0;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "INSERT INTO Book(id, title, author, price, quantity) VALUES (?,?,?,?,?)" ;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, entity.getId());
            ps.setString(2, entity.getTitle());
            ps.setString(3, entity.getAuthor());
            ps.setDouble(4, entity.getPrice());
            ps.setInt(5, entity.getQuantity());
            addedRow = ps.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
             System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return addedRow;
    }

    public int update(Book entity){
        Connection connection = null;
        int updatedRow = 0;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "UPDATE Book SET title = ?, author = ?, price = ?, quantity = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getAuthor());
            ps.setDouble(3, entity.getPrice());
            ps.setInt(4, entity.getQuantity());
            ps.setInt(5, entity.getId());
            updatedRow = ps.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
             System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return updatedRow;
    }

    public int delete(int id){
        Connection connection = null;
        int deletedRow = 0;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "DELETE FROM Book where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            deletedRow = ps.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
             System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return deletedRow;
    }
    
    public ArrayList<Book> getAll(){
        Connection connection = null;
        ArrayList<Book> book_list = new ArrayList<>();
        try {
            connection = dbconnection.connectDatabase();
            String sql = "SELECT * FROM Book";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                book_list.add(new Book(
                    rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                    rs.getDouble("price"), rs.getInt("quantity")
                ));
            }
        } catch (SQLException | DatabaseConnectionException e) {
             System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return book_list;
    }
    
    // Transaction Scope for InvoiceService operations.
    public int reduceStock(int bookId, int quantityToReduce, Connection connection) throws SQLException {
        // CRITICAL: Connection Leak. This function needs to ensure the database/resource connection is properly closed/disposed in the finally block.
        String sql = "UPDATE Book SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, quantityToReduce);
        ps.setInt(2, bookId);
        ps.setInt(3, quantityToReduce); 
        return ps.executeUpdate();
    }
}