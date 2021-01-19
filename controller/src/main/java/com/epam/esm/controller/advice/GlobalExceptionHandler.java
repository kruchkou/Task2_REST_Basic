package com.epam.esm.controller.advice;

import com.epam.esm.controller.model.util.ExceptionResponse;
import com.epam.esm.service.exception.impl.GiftCertificateDataValidationException;
import com.epam.esm.service.exception.impl.GiftCertificateNotFoundException;
import com.epam.esm.service.exception.impl.TagDataValidationException;
import com.epam.esm.service.exception.impl.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final static String RUNTIME_EXCEPTION_MESSAGE_LOCALE = "runtime_exception";
    private final static String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_LOCALE = "illegal_argument_exception";
    private final static String DATA_VALIDATION_EXCEPTION_LOCALE = "data_validation_failed";
    private final static String GIFT_NOT_FOUND_LOCALE = "gifts.not_found";
    private final static String TAG_NOT_FOUND_LOCALE = "tags.not_found";

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e, Locale locale) {
        HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

        String errorMessage = messageSource.getMessage(RUNTIME_EXCEPTION_MESSAGE_LOCALE, new Object[]{}, locale);
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage);

        return new ResponseEntity<>(exceptionResponse, HTTP_STATUS);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e, Locale locale) {
        HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

        String errorMessage = messageSource.getMessage(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_LOCALE, new Object[]{}, locale);
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage);

        return new ResponseEntity<>(exceptionResponse, HTTP_STATUS);
    }

    @ExceptionHandler(GiftCertificateDataValidationException.class)
    public ResponseEntity<ExceptionResponse> handleGiftCertificateDataValidationException(
            GiftCertificateDataValidationException e, Locale locale) {

        HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

        String errorMessage = messageSource.getMessage(DATA_VALIDATION_EXCEPTION_LOCALE, new Object[]{}, locale);
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HTTP_STATUS);
    }

    @ExceptionHandler(TagDataValidationException.class)
    public ResponseEntity<ExceptionResponse> handleTagDataValidationException(
            TagDataValidationException e, Locale locale) {

        final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

        String errorMessage = messageSource.getMessage(DATA_VALIDATION_EXCEPTION_LOCALE, new Object[]{}, locale);
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HTTP_STATUS);
    }

    @ExceptionHandler(GiftCertificateNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleGiftCertificateNotFoundException(
            GiftCertificateNotFoundException e, Locale locale) {

        final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

        String errorMessage = messageSource.getMessage(
                GIFT_NOT_FOUND_LOCALE, new Object[]{e.getNotFoundParameter()}, locale);

        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HTTP_STATUS);
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTagNotFoundException(TagNotFoundException e, Locale locale) {
        final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

        String errorMessage = messageSource.getMessage(
                TAG_NOT_FOUND_LOCALE, new Object[]{e.getNotFoundParameter()}, locale);

        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HTTP_STATUS);
    }
}