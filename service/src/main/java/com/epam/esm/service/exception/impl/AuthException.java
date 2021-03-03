package com.epam.esm.service.exception.impl;

import com.epam.esm.service.exception.ByParameterNotFoundException;
import com.epam.esm.service.exception.ServiceException;

public class AuthException extends ServiceException {

    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, String errorCode) {
        super(message, errorCode);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

    @Override
    public String getErrorCode() {
        return super.getErrorCode();
    }

    @Override
    public void setErrorCode(String errorCode) {
        super.setErrorCode(errorCode);
    }
}
