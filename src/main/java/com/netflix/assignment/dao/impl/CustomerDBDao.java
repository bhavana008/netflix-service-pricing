package com.netflix.assignment.dao.impl;

import com.netflix.assignment.dao.CustomerDao;
import com.netflix.assignment.model.Customer;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * @author bhavana.k on 11/12/18.
 */
public class CustomerDBDao extends AbstractDAO<Customer> implements CustomerDao {

    @Inject
    public CustomerDBDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Customer findById(int id) {
        Customer customer =  get(id);
        System.out.println("customer" + customer);
        return customer;
    }

    public List<Customer> getAllCustomers() {
        return currentSession().createCriteria(Customer.class).list();
    }

    public int create(Customer customer) throws HibernateException {
        return persist(customer).getId();
    }

    public void delete(Customer customer) throws HibernateException {
        currentSession().delete(customer);
    }

    public void update(Customer customer) throws HibernateException {
        currentSession().update(customer);
    }
}
