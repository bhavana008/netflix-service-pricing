package com.netflix.assignment.api;


import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.netflix.assignment.PathConstants;
import com.netflix.assignment.exception.DataException;
import com.netflix.assignment.exception.PriceNotFoundException;
import com.netflix.assignment.model.Customer;
import com.netflix.assignment.model.ServicePlan;
import com.netflix.assignment.service.CustomerManagementService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(PathConstants.CUSTOMER_BASE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerManager {

    private CustomerManagementService customerManagementService;

    @Inject
    public CustomerManager(CustomerManagementService customerManagementService) {
        this.customerManagementService = customerManagementService;
    }

    @POST
    @Timed
    @Path(PathConstants.INSERT)
    @UnitOfWork
    public Response insertCustomer(Customer customer) {
        try {
            customerManagementService.insertCustomer(customer);
        } catch (DataException e) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @POST
    @Timed
    @Path(PathConstants.MODIFY)
    @UnitOfWork
    public Response modifyCustomer(Customer customer) {
        try {
            customerManagementService.modifyCustomer(customer);
        } catch (DataException e) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @POST
    @Timed
    @Path(PathConstants.MODIFY_PLAN_BY_ID)
    @UnitOfWork
    public Response modifyCustomerPlanById(@QueryParam("id") int id, @QueryParam("plan") ServicePlan newPlan) {
        try {
            customerManagementService.modifyCustomerPlanByID(id, newPlan);
        } catch (DataException | PriceNotFoundException e) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @POST
    @Timed
    @Path(PathConstants.DELETE)
    @UnitOfWork
    public Response deleteCustomer(Customer customer) {
        try {
            customerManagementService.deleteCustomer(customer);
        } catch (DataException e) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @POST
    @Timed
    @Path(PathConstants.DELETE_BY_ID)
    @UnitOfWork
    public Response deleteCustomerByID(@QueryParam("id") int id) {
        try {
            customerManagementService.deleteCustomerById(id);
        } catch (DataException e) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @GET
    @Timed
    @Path(PathConstants.GET_BY_ID)
    @UnitOfWork
    public Response getCustomer(@QueryParam("id") int id) {
        return Response.ok(customerManagementService.getCustomerById(id)).build();
    }

    @GET
    @Timed
    @Path(PathConstants.GET_ALL)
    @UnitOfWork
    public Response getAllCustomers() {
        return Response.ok(customerManagementService.getAllCustomers()).build();
    }


    @GET
    @Timed
    @Path(PathConstants.FETCH_PRICE)
    @UnitOfWork
    public Response getCustomerPrice(@QueryParam("id") int id) {
        String price = customerManagementService.getPriceForId(id);
        return Response.ok(price).build();
    }

    @GET
    @Timed
    @Path(PathConstants.FETCH_PRICE_CHANGE)
    @UnitOfWork
    public Response getCustomerPriceChange(@QueryParam("id") int id) {
        String price = customerManagementService.getNewPriceForId(id);
        return Response.ok(price).build();
    }
}
