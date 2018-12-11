package com.netflix.assignment.exception;

/**
 * @author bhavana.k on 11/12/18.
 */
public class DataException extends Exception {

    public DataException() {
        super();
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable e) {
        super(message, e);
    }

}
