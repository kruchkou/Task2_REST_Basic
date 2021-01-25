package com.epam.esm.service.exception.impl;

import com.epam.esm.service.exception.ServiceException;

public class TagAlreadyExistsException extends ServiceException {

    public TagAlreadyExistsException() {
        super();
    }

    public TagAlreadyExistsException(String message) {
        super(message);
    }

    public TagAlreadyExistsException(String message, String errorCode) {
        super(message, errorCode);
    }

    public TagAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagAlreadyExistsException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public TagAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public TagAlreadyExistsException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

}
