package com.netflix.assignment.dao;

import com.netflix.assignment.model.Customer;

import java.util.List;

/**
 * @author bhavana.k on 10/12/18.
 */
public interface CustomerDao {

    Customer findById(int id);

    List<Customer> getAllCustomers();

    int create(Customer customer);

    void delete(Customer customer);

    void update(Customer customer);

}
