package com.netflix.assignment.service;

import com.netflix.assignment.Constants;
import com.netflix.assignment.dao.impl.CustomerDBDao;
import com.netflix.assignment.dao.impl.PriceDBDao;
import com.netflix.assignment.exception.DataException;
import com.netflix.assignment.exception.PriceNotFoundException;
import com.netflix.assignment.model.Customer;
import com.netflix.assignment.model.Price;
import com.netflix.assignment.model.ServicePlan;
import com.netflix.assignment.testutil.DefaultCreationUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author bhavana.k on 11/12/18.
 */
public class CustomerManagementServiceTest {

    private static CustomerDBDao customerDBDao = mock(CustomerDBDao.class);
    private static PriceDBDao priceDBDao = mock(PriceDBDao.class);
    private static CustomerManagementService customerManagementService;

    @BeforeClass
    public static void setup() {
        customerManagementService = new CustomerManagementService(customerDBDao, priceDBDao);
    }

    @Test
    public void TestGetCustomerByIdReturnCustomer() {
        Customer customer = DefaultCreationUtil.createDefaultCustomer(1, ServicePlan.ONE_S);
        when(customerDBDao.findById(customer.getId())).thenReturn(customer);

        Customer actual = customerManagementService.getCustomerById(customer.getId());
        assertEquals(customer, actual);
    }

    @Test
    public void TestGetAllCustomersReturnCustomer() {
        Customer customer1 = DefaultCreationUtil.createDefaultCustomer(1, ServicePlan.ONE_S);
        Customer customer2 = DefaultCreationUtil.createDefaultCustomer(1, ServicePlan.FOUR_S);

        when(customerDBDao.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> actuals = customerManagementService.getAllCustomers();
        assertEquals(Arrays.asList(customer1, customer2), actuals);
    }

    @Test
    public void testInsertCustomerDoNothing() throws DataException {
        Customer expected = DefaultCreationUtil.createDefaultCustomer(1, ServicePlan.ONE_S);
        when(customerDBDao.create(expected)).thenReturn(expected.getId());
        customerManagementService.insertCustomer(expected);
        verify(customerDBDao).create(expected);
    }

    @Test(expected = DataException.class)
    public void testInsertCustomerThrowException() throws DataException {
        Customer expected = DefaultCreationUtil.createDefaultCustomer(2, ServicePlan.TWO_S);
        when(customerDBDao.create(expected)).thenThrow(DataException.class);
        customerManagementService.insertCustomer(expected);
    }

    @Test
    public void testModifyCustomerPlanByIDDoNothing() throws DataException, PriceNotFoundException {
        Customer customer = DefaultCreationUtil.createDefaultCustomer(1, ServicePlan.ONE_S);
        Price latestPrice = DefaultCreationUtil.createPriceForVersion(4, ServicePlan.TWO_S, 3, 56D);

        when(customerDBDao.findById(customer.getId())).thenReturn(customer);
        when(priceDBDao.getByCountryPlanLatest(latestPrice.getCountry(), latestPrice.getPlan())).thenReturn(latestPrice);

        customerManagementService.modifyCustomerPlanByID(1, ServicePlan.TWO_S);

        ArgumentCaptor<Customer> savedCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDBDao).update(savedCaptor.capture());

        assertEquals(latestPrice.getVersion(), savedCaptor.getValue().getVersion());
        assertEquals(latestPrice.getPlan(), savedCaptor.getValue().getPlan());
    }

    @Test
    public void testGetPriceForIdReturnCorrectResult() throws PriceNotFoundException {
        Price price = DefaultCreationUtil.createPriceForVersion(4, ServicePlan.TWO_S, 3, 56D);
        Customer customer = DefaultCreationUtil.createCustomerForPlanAndVersion(2, ServicePlan.TWO_S, 3);

        when(priceDBDao.getByCountryPlanVersion(price.getCountry(), price.getPlan(), price.getVersion())).thenReturn(price);
        when(customerDBDao.findById(customer.getId())).thenReturn(customer);

        String actual = customerManagementService.getPriceForId(customer.getId());
        assertEquals( price.getPrice() + Constants.SPACE + price.getCurrency(), actual);
    }

}
