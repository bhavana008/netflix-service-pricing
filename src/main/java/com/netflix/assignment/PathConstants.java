package com.netflix.assignment;

/**
 * @author bhavana.k on 09/12/18.
 */
public class PathConstants {

    private static final String PATH_DELIMITER = "/";

    public static final String PRICING_BASE_PATH = "pricing";
    public static final String CUSTOMER_BASE_PATH = "customer";
    public static final String INSERT = PATH_DELIMITER + "insert";
    public static final String MODIFY = PATH_DELIMITER + "modify";
    public static final String DELETE = PATH_DELIMITER + "delete";
    public static final String FETCH = PATH_DELIMITER + "fetch";
    public static final String FETCH_PRICE = PATH_DELIMITER + "fetchPrice";
    public static final String FETCH_PRICE_CHANGE = PATH_DELIMITER + "fetchPriceChange";
    public static final String GET_BY_ID = PATH_DELIMITER + "getId";
    public static final String GET_ALL = PATH_DELIMITER + "getAll";
    public static final String MODIFY_FOR_COUNTRY_PLAN = PATH_DELIMITER + "modifyForCountryPlan";
    public static final String DELETE_BY_ID = PATH_DELIMITER + "deleteById";
    public static final String MODIFY_PLAN_BY_ID = PATH_DELIMITER + "modifyPlanById";
}
