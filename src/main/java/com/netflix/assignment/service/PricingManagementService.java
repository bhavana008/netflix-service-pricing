package com.netflix.assignment.service;

import com.google.inject.Inject;
import com.netflix.assignment.dao.PriceDao;
import com.netflix.assignment.exception.DataException;
import com.netflix.assignment.exception.PriceNotFoundException;
import com.netflix.assignment.model.Price;
import com.netflix.assignment.model.ServicePlan;
import org.apache.commons.lang3.SerializationUtils;
import org.hibernate.HibernateException;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author bhavana.k on 10/12/18.
 */
public class PricingManagementService {

    private PriceDao priceDao;

    @Inject
    public PricingManagementService(PriceDao priceDao) {
        this.priceDao = priceDao;
    }

    public Price getPriceById(int id) {
        return priceDao.findById(id);
    }

    public List<Price> getAllPrices() {
        return priceDao.getAllPrice();
    }

    @Transactional
    public void modifyPrice(Price price) throws DataException, PriceNotFoundException {
        try {
            Price currentLatestPrice = priceDao.getByCountryPlanLatest(price.getCountry(), price.getPlan());
            price.setVersion(currentLatestPrice.getVersion() + 1);
            priceDao.create(price);
        } catch (HibernateException e) {
            throw new DataException("Error updating price event : " + price, e);
        }
    }

    @Transactional
    public void modifyPriceCountryService(String country, ServicePlan plan, Double newPrice) throws PriceNotFoundException, DataException {
        try {
            Price price = priceDao.getByCountryPlanLatest(country, plan);
            priceDao.updateWithNewEntry(price, newPrice);
        } catch (HibernateException e) {
            throw new DataException("Error updating price event for : " + country + " " + plan, e);
        }
    }

    @Transactional
    public void insertPrice(Price price) throws DataException {
        try {
            price.setVersion(1);
            priceDao.create(price);
        } catch (HibernateException e) {
            throw new DataException("Error inserting price event : " + price, e);
        }
    }

    @Transactional
    public void deletePrice(Price price) throws DataException {
        try {
            priceDao.delete(price);
        } catch (HibernateException e) {
            throw new DataException("Error inserting price event : " + price, e);
        }
    }

    @Transactional
    public void deletePriceForId(int id) throws DataException {
        try {
            priceDao.delete(priceDao.findById(id));
        } catch (HibernateException e) {
            throw new DataException("Error inserting price event : " + id, e);
        }
    }
}
