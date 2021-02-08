package com.epam.esm.service.exception.impl;

import com.epam.esm.service.exception.ByParameterNotFoundException;

public class GiftCertificateByParameterNotFoundException extends ByParameterNotFoundException {

    public GiftCertificateByParameterNotFoundException() {
    }

    public GiftCertificateByParameterNotFoundException(String message) {
        super(message);
    }

    public GiftCertificateByParameterNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public GiftCertificateByParameterNotFoundException(String message, String errorCode, String notFoundParameter) {
        super(message, errorCode, notFoundParameter);
    }

    public GiftCertificateByParameterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GiftCertificateByParameterNotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public GiftCertificateByParameterNotFoundException(String message, Throwable cause, String errorCode, String notFoundParameter) {
        super(message, cause, errorCode, notFoundParameter);
    }

    public GiftCertificateByParameterNotFoundException(Throwable cause) {
        super(cause);
    }

    public GiftCertificateByParameterNotFoundException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

    public GiftCertificateByParameterNotFoundException(Throwable cause, String errorCode, String notFoundParameter) {
        super(cause, errorCode, notFoundParameter);
    }
}