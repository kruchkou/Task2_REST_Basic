package com.epam.esm.service.exception.impl;

import com.epam.esm.service.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public UserNotFoundException(String message, String errorCode, String notFoundParameter) {
        super(message, errorCode, notFoundParameter);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public UserNotFoundException(String message, Throwable cause, String errorCode, String notFoundParameter) {
        super(message, cause, errorCode, notFoundParameter);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

    public UserNotFoundException(Throwable cause, String errorCode, String notFoundParameter) {
        super(cause, errorCode, notFoundParameter);
    }
}
