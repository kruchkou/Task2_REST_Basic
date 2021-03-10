package com.epam.esm.service.exception.impl;

import com.epam.esm.service.exception.ByParameterNotFoundException;

public class OrderNotFoundException extends ByParameterNotFoundException {

    public OrderNotFoundException() {
        super();
    }

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public OrderNotFoundException(String message, String errorCode, String notFoundParameter) {
        super(message, errorCode, notFoundParameter);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public OrderNotFoundException(String message, Throwable cause, String errorCode, String notFoundParameter) {
        super(message, cause, errorCode, notFoundParameter);
    }

    public OrderNotFoundException(Throwable cause) {
        super(cause);
    }

    public OrderNotFoundException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

    public OrderNotFoundException(Throwable cause, String errorCode, String notFoundParameter) {
        super(cause, errorCode, notFoundParameter);
    }
}
