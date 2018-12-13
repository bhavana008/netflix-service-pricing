package com.netflix.assignment.testutil;

import com.netflix.assignment.model.Customer;
import com.netflix.assignment.model.Price;
import com.netflix.assignment.model.ServicePlan;

/**
 * @author bhavana.k on 11/12/18.
 */

public class DefaultCreationUtil {
    public static Price createDefaultPrice(int priceId, ServicePlan plan) {
        Price price = new Price();

        //price.setId(priceId);
        price.setVersion(1);
        price.setPlan(plan);
        price.setCurrency("RS");
        price.setCountry("India");
        price.setPrice(45D);
        return price;
    }
    public static Price createPriceForVersion(int priceId, ServicePlan plan, int version, Double priceValue) {
        Price price = new Price();

        //price.setId(priceId);
        price.setVersion(version);
        price.setPlan(plan);
        price.setCurrency("RS");
        price.setCountry("India");
        price.setPrice(priceValue);
        return price;
    }

    public static Customer createDefaultCustomer(int customerId, ServicePlan plan) {
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Bhavana");
        customer.setCountry("India");
        customer.setPlan(plan);
        customer.setVersion(1);
        return customer;
    }

    public static Customer createCustomerForPlanAndVersion(int customerId, ServicePlan plan, int version) {
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Bhavana");
        customer.setCountry("India");
        customer.setPlan(plan);
        customer.setVersion(version);
        return customer;
    }
}
