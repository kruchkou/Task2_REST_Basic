package com.epam.esm.service.exception.impl;

import com.epam.esm.service.exception.ServiceException;

public class GiftCertificateDataValidationException extends ServiceException {

    public GiftCertificateDataValidationException() {
    }

    public GiftCertificateDataValidationException(String message) {
        super(message);
    }

    public GiftCertificateDataValidationException(String message, String errorCode) {
        super(message, errorCode);
    }

    public GiftCertificateDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GiftCertificateDataValidationException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public GiftCertificateDataValidationException(Throwable cause) {
        super(cause);
    }

    public GiftCertificateDataValidationException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

}
