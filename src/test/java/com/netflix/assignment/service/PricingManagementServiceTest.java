package com.netflix.assignment.service;

import com.netflix.assignment.dao.impl.PriceDBDao;
import com.netflix.assignment.exception.DataException;
import com.netflix.assignment.model.Price;
import com.netflix.assignment.model.ServicePlan;
import com.netflix.assignment.testutil.DefaultCreationUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * @author bhavana.k on 11/12/18.
 */
public class PricingManagementServiceTest {

    private static PriceDBDao priceDBDao = mock(PriceDBDao.class);
    private static PricingManagementService pricingManagementService;

    @BeforeClass
    public static void setup() {
        pricingManagementService = new PricingManagementService(priceDBDao);
    }

    @Test
    public void TestGetPriceByIdReturnPrice() {
        Price price = DefaultCreationUtil.createDefaultPrice(1, ServicePlan.ONE_S);
        when(priceDBDao.findById(price.getId())).thenReturn(price);

        Price actual = pricingManagementService.getPriceById(price.getId());
        assertEquals(price, actual);
    }

    @Test
    public void TestGetAllPriceReturnPrice() {
        Price price1 = DefaultCreationUtil.createDefaultPrice(1, ServicePlan.ONE_S);
        Price price2 = DefaultCreationUtil.createDefaultPrice(2, ServicePlan.TWO_S);

        when(priceDBDao.getAllPrice()).thenReturn(Arrays.asList(price1, price2));
        List<Price> actuals = pricingManagementService.getAllPrices();
        assertEquals(Arrays.asList(price1, price2), actuals);
    }

    @Test
    public void testInsertPriceDoNothing() throws DataException {
        Price expected = DefaultCreationUtil.createDefaultPrice(1, ServicePlan.ONE_S);
        when(priceDBDao.create(expected)).thenReturn(expected.getId());
        pricingManagementService.insertPrice(expected);
        verify(priceDBDao).create(expected);
    }

    @Test(expected = DataException.class)
    public void testInsertPriceThrowsException() throws DataException {
        Price expected = DefaultCreationUtil.createDefaultPrice(2, ServicePlan.ONE_S);
        when(priceDBDao.create(expected)).thenThrow(DataException.class);
        pricingManagementService.insertPrice(expected);
    }
}
