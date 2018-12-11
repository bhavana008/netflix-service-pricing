package com.netflix.assignment.app;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.netflix.assignment.dao.CustomerDao;
import com.netflix.assignment.dao.PriceDao;
import com.netflix.assignment.dao.impl.CustomerDBDao;
import com.netflix.assignment.dao.impl.PriceDBDao;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;

/**
 * @author bhavana.k on 10/12/18.
 */
@AllArgsConstructor
public class GuiceInjectorModule extends AbstractModule {

    SessionFactory sessionFactory;
    UnitOfWorkAwareProxyFactory unitOfWorkAwareProxyFactory;

    protected void configure() {
/*        bind(PricingManagementService.class).toInstance(unitOfWorkAwareProxyFactory.create(PricingManagementService.class));

        bind(PricingManagementService.class).toInstance(
                new PricingManagementService(new CustomerDao(getSessionFactory()), new PriceDao(getSessionFactory())));*/
    }

    @Provides
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Provides
    public CustomerDao getCustomerDao() {
        return new CustomerDBDao(getSessionFactory());
    }

    @Provides
    public PriceDao getPriceDao() {
        return new PriceDBDao(getSessionFactory());
    }
}
