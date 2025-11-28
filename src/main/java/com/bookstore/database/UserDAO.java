package bookstore.database;

import com.bookstore.model.User;
import bookstore.exception.DatabaseConnectionException;
import java.sql.*;
import java.util.ArrayList;

public class UserDAO extends abstractGenericDAO<User> {
    
    public User findByID(String id){
        Connection connection = null;
        User user = null;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "SELECT * FROM User WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(
                    rs.getString("id"), rs.getString("username"), rs.getString("password"),
                    rs.getString("role"), rs.getInt("salary")
                );
            }
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return user;
    }
    
    public User findByUsername(String username){
        Connection connection = null;
        User user = null;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "SELECT * FROM User WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                user = new User(
                    rs.getString("id"), rs.getString("username"), rs.getString("password"),
                    rs.getString("role"), rs.getInt("salary")
                );
            }
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return user;
    }
    
    public int insert(User entity){
        Connection connection = null;
        int addedRow = 0;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "INSERT INTO User(id, username, password, role, salary) VALUES (?,?,?,?,?)" ;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getUserid());
            ps.setString(2, entity.getUsername());
            ps.setString(3, entity.getPassword());
            ps.setString(4, entity.getRole());
            ps.setInt(5, entity.getSalary());
            addedRow = ps.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return addedRow;
    }
    
    public int update(User entity){
        Connection connection = null;
        int updatedRow = 0;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "UPDATE User SET username = ?, role = ?, salary = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getRole());
            ps.setInt(3, entity.getSalary());
            ps.setString(4, entity.getUserid());
            
            updatedRow = ps.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return updatedRow;
    }
    
    public int updatePassword(String userId, String newHashedPassword){
        Connection connection = null;
        int updatedRow = 0;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "UPDATE User SET password = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newHashedPassword);
            ps.setString(2, userId);
            
            updatedRow = ps.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
             System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return updatedRow;
    }
    
    public int delete(String id){
        Connection connection = null;
        int deletedRow = 0;
        try {
            connection = dbconnection.connectDatabase();
            String sql = "DELETE FROM User where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            deletedRow = ps.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return deletedRow;
    }

    public ArrayList<User> getAll(){
        Connection connection = null;
        ArrayList<User> user_list = new ArrayList<>();
        try {
            connection = dbconnection.connectDatabase();
            String sql = "SELECT * FROM User";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user_list.add(new User(
                    rs.getString("id"), rs.getString("username"), rs.getString("password"),
                    rs.getString("role"), rs.getInt("salary")
                ));
            }
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return user_list;
    }
}