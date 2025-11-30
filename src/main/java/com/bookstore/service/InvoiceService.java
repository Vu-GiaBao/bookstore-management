package com.bookstore.service;

import com.bookstore.database.InvoiceDAO;
import com.bookstore.model.Invoice;

import java.util.ArrayList;
import java.util.List;

public class InvoiceService {

    private final InvoiceDAO invoiceDAO = new InvoiceDAO();

    public List<Invoice> getAll() {
        return invoiceDAO.getAll();
    }

    public void save(Invoice invoice) {
        invoiceDAO.insert(invoice);
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
}
