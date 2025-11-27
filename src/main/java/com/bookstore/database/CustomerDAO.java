package bookstore.database;

import com.bookstore.model.Customer;
import bookstore.exception.DatabaseConnectionException;
import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO extends abstractGenericDAO<Customer> {
    
    public Customer findByID(int id){
        Connection connection = null;
        Customer customer = null;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "SELECT * FROM Customer WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id); 
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                customer = new Customer(
                    rs.getInt("id"), rs.getString("name"), 
                    rs.getString("email"), rs.getString("phone")
                );
            }
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return customer;
    }
    
    public int insert(Customer entity){
        Connection connection = null;
        int addedRow = 0;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "INSERT INTO Customer(id, name, email, phone) VALUES (?,?,?,?)" ;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, entity.getId());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getPhone());
            addedRow = ps.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
             System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return addedRow;
    }

    public int update(Customer entity){
        Connection connection = null;
        int updatedRow = 0;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "UPDATE Customer SET name = ?, email = ?, phone = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getPhone());
            ps.setInt(4, entity.getId());
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
            String sql = "DELETE FROM Customer where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            deletedRow = ps.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
             System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return deletedRow;
    }
}