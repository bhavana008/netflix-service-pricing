package com.netflix.assignment.dao.impl;

import com.netflix.assignment.dao.PriceDao;
import com.netflix.assignment.exception.PriceNotFoundException;
import com.netflix.assignment.model.Price;
import com.netflix.assignment.model.ServicePlan;
import io.dropwizard.hibernate.AbstractDAO;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * @author bhavana.k on 11/12/18.
 */
public class PriceDBDao extends AbstractDAO<Price> implements PriceDao {

    @Inject
    public PriceDBDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Price> getAllPrice() {
        return currentSession().createCriteria(Price.class).list();
    }

    public Price findById(int id) {
        return get(id);
    }

    public int create(Price price) throws HibernateException {
        return persist(price).getId();
    }

    public int updateWithNewEntry(Price price, Double newPrice) throws HibernateException {
        currentSession().evict(price);
        price.setPrice(newPrice);
        price.setVersion(price.getVersion() + 1);
        return ((Integer) currentSession().save(price));
    }

    public void delete(Price price) throws HibernateException {
        currentSession().delete(price);
    }

    public Price getByCountryPlanLatest(String country, ServicePlan plan) throws PriceNotFoundException {
        List<Price> prices;
        try {
            prices = (List<Price>) currentSession().createQuery("from Price where country= '" + country
                    + "' and plan= '"
                    + plan.toString()
                    + "' ORDER BY version DESC").list();

            if(CollectionUtils.isNotEmpty(prices)) {
                return prices.get(0);
            }

        } catch (HibernateException e) {
            throw new PriceNotFoundException("Error fetching price for country");
        }

        return null;
    }

    public Price getByCountryPlanVersion(String country, ServicePlan plan, int version) throws PriceNotFoundException {
        List<Price> prices;
        try {
            prices = (List<Price>) currentSession().createQuery("from Price where country= '" + country
                    + "' and plan= '"
                    + plan.toString()
                    + "' and version = '" + version + "'").list();

            if(CollectionUtils.isNotEmpty(prices)) {
                return prices.get(0);
            }

        } catch (HibernateException e) {
            throw new PriceNotFoundException("Error fetching price for country");
        }

        return null;
    }
}