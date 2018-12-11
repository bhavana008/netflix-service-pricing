package com.netflix.assignment.api;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.netflix.assignment.PathConstants;
import com.netflix.assignment.exception.DataException;
import com.netflix.assignment.exception.PriceNotFoundException;
import com.netflix.assignment.model.Price;
import com.netflix.assignment.service.PricingManagementService;
import com.netflix.assignment.model.ServicePlan;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author bhavana.k on 09/12/18.
 */
@Path(PathConstants.PRICING_BASE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class PricingManager {

    private PricingManagementService pricingManagementService;

    @Inject
    public PricingManager (PricingManagementService pricingManagementService) {
        this.pricingManagementService = pricingManagementService;
    }

    @POST
    @Timed
    @Path(PathConstants.INSERT)
    @UnitOfWork
    public Response insertPricing(Price price) {
        try {
            pricingManagementService.insertPrice(price);
        } catch (DataException e) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @POST
    @Timed
    @Path(PathConstants.MODIFY)
    @UnitOfWork
    public Response modifyPricing(Price price) {
        try {
            pricingManagementService.modifyPrice(price);
        } catch (DataException e) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @POST
    @Timed
    @Path(PathConstants.MODIFY_FOR_COUNTRY_PLAN)
    @UnitOfWork
    public Response modifyPricingForCountryPlan(
            @QueryParam("country") String country,
            @QueryParam("plan")ServicePlan plan,
            @QueryParam("new_price") Double newPrice
    ) throws PriceNotFoundException {
        System.out.println("Country: " + country + " plan: " + plan);
        try {
            pricingManagementService.modifyPriceCountryService(country, plan, newPrice);
        } catch (DataException e) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @POST
    @Timed
    @Path(PathConstants.DELETE)
    @UnitOfWork
    public Response deletePricing(Price price) {
        try {
            pricingManagementService.deletePrice(price);
        } catch (DataException e) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @POST
    @Timed
    @Path(PathConstants.DELETE_BY_ID)
    @UnitOfWork
    public Response deletePricingForID(@QueryParam("id") int id) {
        try {
            pricingManagementService.deletePriceForId(id);
        } catch (DataException e) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @GET
    @Timed
    @Path(PathConstants.GET_BY_ID)
    @UnitOfWork
    public Response getPrice(@QueryParam("id") int id) {
        return Response.ok(pricingManagementService.getPriceById(id)).build();
    }

    @GET
    @Timed
    @Path(PathConstants.GET_ALL)
    @UnitOfWork
    public Response getAllPrices() {
        return Response.ok(pricingManagementService.getAllPrices()).build();
    }

}
