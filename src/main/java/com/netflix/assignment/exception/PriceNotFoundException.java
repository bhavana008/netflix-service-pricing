package com.netflix.assignment.exception;

/**
 * @author bhavana.k on 11/12/18.
 */
public class PriceNotFoundException extends Exception {

    public PriceNotFoundException() {
        super();
    }

    public PriceNotFoundException (String message) {
        super(message);
    }
}
