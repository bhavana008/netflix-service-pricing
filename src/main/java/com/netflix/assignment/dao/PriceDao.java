package com.netflix.assignment.dao;

import com.netflix.assignment.exception.PriceNotFoundException;
import com.netflix.assignment.model.Price;
import com.netflix.assignment.model.ServicePlan;

import java.util.List;

/**
 * @author bhavana.k on 10/12/18.
 */
public interface PriceDao {

    String PRICE_NOT_FOUND = "Price Not Found !!";

    List<Price> getAllPrice();

    Price findById(int id);

    int create(Price price);

    void delete(Price price);

    int updateWithNewEntry(Price price, Double newPrice);

    Price getByCountryPlanLatest(String country, ServicePlan plan) throws PriceNotFoundException;

    Price getByCountryPlanVersion(String country, ServicePlan plan, int version) throws PriceNotFoundException;
}
