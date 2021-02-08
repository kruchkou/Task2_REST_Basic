package com.epam.esm.service.exception.impl;

import com.epam.esm.service.exception.ServiceException;

public class GiftCertificateByParametersNotFoundException extends ServiceException {

    public GiftCertificateByParametersNotFoundException() {
    }

    public GiftCertificateByParametersNotFoundException(String message) {
        super(message);
    }

    public GiftCertificateByParametersNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public GiftCertificateByParametersNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GiftCertificateByParametersNotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public GiftCertificateByParametersNotFoundException(Throwable cause) {
        super(cause);
    }

    public GiftCertificateByParametersNotFoundException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

}