package bookstore.database;

import com.bookstore.model.InvoiceItem;
import java.sql.*;
import java.util.List;
import com.bookstore.model.Book;
import bookstore.exception.DatabaseConnectionException;
import java.util.ArrayList;

public class InvoiceItemDAO extends abstractGenericDAO<InvoiceItem> {
    
    private BookDAO bookDAO = new BookDAO();

    // Transaction Scope for InvoiceService operations.
    public int[] insertBatch(String invoiceId, List<InvoiceItem> items, Connection existingConnection) throws SQLException {
        // Transaction Context: This method accepts an external, pre-opened Connection object to ensure all operations run within a single, cohesive database transaction.
        String sql = "INSERT INTO InvoiceItem(invoice_id, book_id, quantity, price_at_sale) VALUES (?,?,?,?)" ;
        PreparedStatement ps = existingConnection.prepareStatement(sql);
        
        for (InvoiceItem entity : items) {
            ps.setString(1, invoiceId);
            ps.setInt(2, entity.getBook().getId());
            ps.setInt(3, entity.getQuantity());
            // Stores the unit price effective at the time of transaction.
            ps.setDouble(4, entity.getPrice() / entity.getQuantity()); 
            ps.addBatch();
        }
        
        return ps.executeBatch();
    }
    
    // QUERY/FETCH BY INVOICE ID
    public ArrayList<InvoiceItem> findByInvoiceID(String invoiceId){
        Connection connection = null;
        ArrayList<InvoiceItem> itemList = new ArrayList<>();
        try {
            connection = dbconnection.connectDatabase();
            String sql = "SELECT * FROM InvoiceItem WHERE invoice_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, invoiceId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                int quantity = rs.getInt("quantity");
                // FETCH/RETRIEVE Book details using the BookDAO.
                Book book = bookDAO.findByID(bookId); 
                
                if (book != null) {
                    itemList.add(new InvoiceItem(book, quantity));
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error: " + (e instanceof SQLException ? SQLExceptionHandler((SQLException)e) : e.getMessage()));
        } finally { closeConnection(connection); }
        return itemList;
    }
}