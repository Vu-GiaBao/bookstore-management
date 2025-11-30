package com.bookstore.service;

import com.bookstore.database.CustomerDAO;
import com.bookstore.model.Customer;

import java.util.List;

public class CustomerService {
    private final CustomerDAO customerDAO = new CustomerDAO();

    public int addCustomer(Customer customer) {
        if (customer == null) {
            return 0;
        }
        return customerDAO.insert(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAll();
    }
    
    public List<Customer> getAll() {
        return getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return customerDAO.findById(id);
    }

    public List<Customer> getCustomersByName(String name) {
        return customerDAO.findByName(name);
    }

    public Customer getCustomerByEmail(String email) {
        return customerDAO.findByEmail(email);
    }

    public Customer getCustomerByPhone(String phone) {
        return customerDAO.findByPhone(phone);
    }

    public int updateCustomer(Customer customer) {
    return customerDAO.update(customer);
    }

    public boolean deleteCustomer(int id) {
        return customerDAO.delete(id) > 0;
    }
    
    public Customer findCustomerById(int id) {
        return customerDAO.findById(id);
    }

}