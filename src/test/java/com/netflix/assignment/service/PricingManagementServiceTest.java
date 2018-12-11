package com.netflix.assignment.service;

import com.netflix.assignment.dao.impl.PriceDBDao;
import com.netflix.assignment.model.Price;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * @author bhavana.k on 11/12/18.
 */
public class PricingManagementServiceTest {

    private static PriceDBDao priceDBDao = mock(PriceDBDao.class);
    private static PricingManagementService pricingManagementService;

//    @BeforeClass
//    public static void setup() {
//        pricingManagementService = new PricingManagementService(priceDBDao);
//    }

//    @Test
//    public void testGetAuditByIdReturnAudit() {
//        Price expected = MetricTestUtil.createAuditLog("1",TimeTestUtil.getLongDate());
//        when(auditDao.findById(expected.getId())).thenReturn(expected);
//        Audit actual =  auditManagementService.getAuditById(expected.getId());
//        assertEquals(actual.getMetricId(),expected.getMetricId());
//    }
}
