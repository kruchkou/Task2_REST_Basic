package com.epam.esm.service.exception.impl;

import com.epam.esm.service.exception.ServiceException;

public class UserAlreadyExistsException extends ServiceException {

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, String errorCode) {
        super(message, errorCode);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyExistsException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

}
