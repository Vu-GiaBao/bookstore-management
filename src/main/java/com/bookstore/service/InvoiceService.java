package com.bookstore.service;

import com.bookstore.database.InvoiceDAO;
import com.bookstore.database.InvoiceItemDAO;
import com.bookstore.model.Invoice;
import com.bookstore.model.InvoiceItem;
import com.bookstore.model.InvoiceStatus;

import java.util.ArrayList;
import java.util.List;

public class InvoiceService {

    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final InvoiceItemDAO itemDAO = new InvoiceItemDAO();

    public List<Invoice> getAll() {
        return invoiceDAO.getAll();
    }

    public void save(Invoice invoice) {
        invoiceDAO.insert(invoice);

        if (invoice.getItems() != null && !invoice.getItems().isEmpty()) {
            itemDAO.insertBatch(invoice.getInvoiceId(), invoice.getItems());
        }
    }


    public void delete(String invoiceId) {
        invoiceDAO.delete(invoiceId);
    }

    public List<Invoice> searchInvoice(String keyword) {
        List<Invoice> all = getAll();

        if (keyword == null || keyword.trim().isEmpty()) {
            return all;
        }

        String lower = keyword.trim().toLowerCase();
        List<Invoice> result = new ArrayList<>();

        for (Invoice inv : all) {
            if (inv.getInvoiceId() != null &&
                inv.getInvoiceId().toLowerCase().contains(lower)) {
                result.add(inv);
            }
        }

        return result;
    }

    public List<InvoiceItem> getItems(String invoiceId) {
        return itemDAO.getByInvoiceId(invoiceId);
    }

    public void updateStatus(String invoiceId, InvoiceStatus status) {
        invoiceDAO.updateStatus(invoiceId, status);
    }

}
