package com.epam.esm.service.exception.impl;

import com.epam.esm.service.exception.ServiceException;

public class DataValidationException extends ServiceException {

    public DataValidationException() {
        super();
    }

    public DataValidationException(String message) {
        super(message);
    }

    public DataValidationException(String message, String errorCode) {
        super(message, errorCode);
    }

    public DataValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataValidationException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public DataValidationException(Throwable cause) {
        super(cause);
    }

    public DataValidationException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

}
