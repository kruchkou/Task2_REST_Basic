package com.epam.esm.service.exception;

public abstract class ByParameterNotFoundException extends ServiceException {

    private String notFoundParameter;

    public String getNotFoundParameter() {
        return notFoundParameter;
    }

    public void setNotFoundParameter(String notFoundParameter) {
        this.notFoundParameter = notFoundParameter;
    }

    public ByParameterNotFoundException() {
    }

    public ByParameterNotFoundException(String message) {
        super(message);
    }

    public ByParameterNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ByParameterNotFoundException(String message, String errorCode, String notFoundParameter) {
        super(message, errorCode);
        this.notFoundParameter = notFoundParameter;
    }


    public ByParameterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ByParameterNotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public ByParameterNotFoundException(String message, Throwable cause, String errorCode, String notFoundParameter) {
        super(message, cause, errorCode);
        this.notFoundParameter = notFoundParameter;
    }


    public ByParameterNotFoundException(Throwable cause) {
        super(cause);
    }

    public ByParameterNotFoundException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

    public ByParameterNotFoundException(Throwable cause, String errorCode, String notFoundParameter) {
        super(cause, errorCode);
        this.notFoundParameter = notFoundParameter;
    }
}
