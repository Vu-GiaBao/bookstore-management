package com.bookstore.service;

import com.bookstore.database.InvoiceDAO;
import com.bookstore.database.InvoiceItemDAO;
import com.bookstore.model.Book;
import com.bookstore.model.Invoice;
import com.bookstore.model.InvoiceItem;
import com.bookstore.model.InvoiceStatus;

import java.util.Iterator;
import java.util.UUID;

public class InvoiceService {
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final InvoiceItemDAO invoiceItemDAO = new InvoiceItemDAO();


    public Invoice createInvoice() {
        String randomId = UUID.randomUUID().toString();  
        return new Invoice(randomId);
    }


    public void addItem(Invoice invoice, Book book, int quantity) {
        if (invoice.getStatus() == InvoiceStatus.PAID) {
            System.out.println("Error! Invoice is already paid.");
            return;
        }
        if (quantity <= 0) {
            System.out.println("Error! Quantity must be greater than 0.");
            return;
        }
        invoice.getItems().add(new InvoiceItem(book, quantity));
        System.out.println("Added: " + book.getTitle() + " x" + quantity);
    }


    public void removeItem(Invoice invoice, Book book) {
        if (invoice.getStatus() == InvoiceStatus.PAID) {
            System.out.println("Error! Invoice is already paid.");
            return;
        }
        if (invoice.getItems().removeIf(item -> item.getBook().equals(book))) {
            System.out.println("Removed successfully!");
        } else {
            System.out.println("Book not found in invoice!");
        }
    }


    public void updateItem(Invoice invoice, String bookId, int newQuantity) {
        if (invoice.getStatus() == InvoiceStatus.PAID) {
            System.out.println("Error! Invoice is already paid.");
            return;
        }
        for (InvoiceItem iem: invoice.getItems()) {
            if (item.getBook().getId().equals(bookId)) {
                if (newQuantity <= 0) {
                    invoice.getItems().remove(item);
                    System.out.println("Item removed from invoice successfully.");
                    return;
                }
                int oldQuantity = item.getQuantity();
                item.updateQuantity(newQuantity);
                System.out.println("Updated quantity: " + oldQty + " → " + newQuantity);
            }
        }
    }

    
    public double calculateTotal(Invoice invoice) {
        double total = 0;
        for (InvoiceItem item : invoice.getItems()) {
            total += item.getPrice();
        }
        invoice.setTotalAmount(total);
        return total;
    }


    public void paymentStatus(Invoice invoice) {
        if (invoice.getItems().isEmpty()) {
            System.out.println("Error! Invoice is empty.");
            return;
        }
        if (invoice.getStatus() == InvoiceStatus.PAID) {
            System.out.println("Error! Invoice has been paid before.");
            return;
        }
        for (InvoiceItem item : invoice.getItems()) {
            Book book = item.getBook();
            if (book.getQuantity() < item.getQuantity()) {
                System.out.println("Error! Stock changed, not enough for: " + book.getTitle()
                        + " (available " + book.getQuantity() + ")");
                return;
            }
        }
        calculateTotal(invoice);
        invoice.setStatus(InvoiceStatus.PAID);
        int invoiceRows = invoiceDAO.insert(invoice);
        if (invoiceRows <= 0) {
            System.out.println("Payment failed! Could not insert Invoice to DB.");
            return;
        }
        int[] itemRows = invoiceItemDAO.insertBatch(invoice.getInvoiceId(), invoice.getItems());
        if (itemRows == null || itemRows.length == 0) {
            System.out.println("Payment warning: Invoice inserted but items were not inserted.");
            return;
        }
        for (InvoiceItem item : invoice.getItems()) {
            Book book = item.getBook();
            int qty = item.getQuantity();
            book.setQuantity(book.getQuantity() - qty);
        }
        System.out.println("Paid successfully!");
    }


    public void displayInvoiceFromDB(String invoiceId) {
        Invoice invoice = invoiceDAO.findByID(invoiceId); 
        if (invoice == null) {
            System.out.println("Invoice not found!");
            return;
        }
        invoice.setItems(invoiceItemDAO.findByInvoiceID(invoiceId));
        invoice.displayInfo();
    }
}
