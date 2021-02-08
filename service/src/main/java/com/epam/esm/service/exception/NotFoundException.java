package com.epam.esm.service.exception;

public abstract class NotFoundException extends ServiceException {

    private String notFoundParameter;

    public String getNotFoundParameter() {
        return notFoundParameter;
    }

    public void setNotFoundParameter(String notFoundParameter) {
        this.notFoundParameter = notFoundParameter;
    }

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public NotFoundException(String message, String errorCode, String notFoundParameter) {
        super(message, errorCode);
        this.notFoundParameter = notFoundParameter;
    }


    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public NotFoundException(String message, Throwable cause, String errorCode, String notFoundParameter) {
        super(message, cause, errorCode);
        this.notFoundParameter = notFoundParameter;
    }


    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

    public NotFoundException(Throwable cause, String errorCode, String notFoundParameter) {
        super(cause, errorCode);
        this.notFoundParameter = notFoundParameter;
    }
}
