package com.epam.esm.controller.model.util;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ExceptionResponse {

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public ExceptionResponse(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
