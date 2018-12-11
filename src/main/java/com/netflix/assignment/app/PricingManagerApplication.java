package com.netflix.assignment.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.netflix.assignment.api.CustomerManager;
import com.netflix.assignment.api.PricingManager;
import com.netflix.assignment.config.PricingConfiguration;
import com.netflix.assignment.model.Customer;
import com.netflix.assignment.model.Price;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author bhavana.k on 10/12/18.
 */
public class PricingManagerApplication extends Application<PricingConfiguration> {
    public void run(PricingConfiguration pricingConfiguration, Environment environment) throws Exception {

        UnitOfWorkAwareProxyFactory factory = new UnitOfWorkAwareProxyFactory(hibernateMonitoring);
        Injector injector = Guice.createInjector(new GuiceInjectorModule(hibernateMonitoring.getSessionFactory()
                , factory));

        environment.jersey().register(injector.getInstance(CustomerManager.class));
        environment.jersey().register(injector.getInstance(PricingManager.class));

    }

    private final HibernateBundle<PricingConfiguration> hibernateMonitoring = new HibernateBundle<PricingConfiguration>(Price.class, Customer.class) {

        public DataSourceFactory getDataSourceFactory(PricingConfiguration configuration) {
            return configuration.getDatabase();
        }
    };

    public static void main(String args[]) throws Exception {
        new PricingManagerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<PricingConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateMonitoring);
    }
}
