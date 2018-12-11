package com.netflix.assignment.service;

import com.google.inject.Inject;
import com.netflix.assignment.Constants;
import com.netflix.assignment.dao.CustomerDao;
import com.netflix.assignment.dao.PriceDao;
import com.netflix.assignment.exception.DataException;
import com.netflix.assignment.exception.PriceNotFoundException;
import com.netflix.assignment.model.Customer;
import com.netflix.assignment.model.Price;
import com.netflix.assignment.model.ServicePlan;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
public class CustomerManagementService {

    private CustomerDao customerDao;
    private PriceDao priceDao;

    @Inject
    public CustomerManagementService(CustomerDao customerDao, PriceDao priceDao) {
        this.customerDao = customerDao;
        this.priceDao = priceDao;
    }

    public Customer getCustomerById(int id) {
        return customerDao.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    @Transactional
    public void modifyCustomer(Customer customer) throws DataException {
        log.debug("Modifying customer with Id : {}", customer.getId());
        try {
            customerDao.update(customer);
        } catch (HibernateException e) {
            throw new DataException("Error updating price event : " + customer, e);
        }
    }

    @Transactional
    public void modifyCustomerPlanByID(int id, ServicePlan plan) throws DataException {
        log.debug("Modifying customer with Id : {}", id);
        try {
            Customer customer = customerDao.findById(id);
            customer.setPlan(plan);
            customerDao.update(customer);
        } catch (HibernateException e) {
            throw new DataException("Error updating price event : " + id, e);
        }
    }

    @Transactional
    public void insertCustomer(Customer customer) throws DataException {
        log.debug("Adding customer : {}", customer);
        try {
            customerDao.create(customer);
        } catch (HibernateException e) {
            throw new DataException("Error inserting price event : " + customer, e);
        }
    }

    @Transactional
    public void deleteCustomer(Customer customer) throws DataException {
        log.debug("Deleting customer : {}", customer);
        try {
            customerDao.delete(customer);
        } catch (HibernateException e) {
            throw new DataException("Error inserting price event : " + customer, e);
        }
    }

    @Transactional
    public void deleteCustomerById(int id) throws DataException {
        log.debug("Deleting customer : {}", id);
        try {
            customerDao.delete(customerDao.findById(id));
        } catch (HibernateException e) {
            throw new DataException("Error inserting price event : " + id, e);
        }
    }

    @Transactional
    public String getPriceForId(int id) {
        log.debug("Getting price for customer with Id : {}", id);

        Customer customer = customerDao.findById(id);

        try {
            Price price = priceDao.getByCountryPlan(customer.getCountry(), customer.getPlan());
            return price.getPrice() + Constants.SPACE + price.getCurrency();
        } catch (PriceNotFoundException e) {
            return PriceDao.PRICE_NOT_FOUND;
        }
    }
}
