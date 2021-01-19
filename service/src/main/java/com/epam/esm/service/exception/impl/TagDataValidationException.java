package com.epam.esm.service.exception.impl;

import com.epam.esm.service.exception.ServiceException;

public class TagDataValidationException extends ServiceException {

    public TagDataValidationException() {
        super();
    }

    public TagDataValidationException(String message) {
        super(message);
    }

    public TagDataValidationException(String message, String errorCode) {
        super(message, errorCode);
    }

    public TagDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagDataValidationException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public TagDataValidationException(Throwable cause) {
        super(cause);
    }

    public TagDataValidationException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

}
