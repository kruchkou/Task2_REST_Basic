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

    private static final String RUNTIME_EXCEPTION_MESSAGE_LOCALE = "runtime_exception";
    private static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_LOCALE = "illegal_argument_exception";
    private static final String DATA_VALIDATION_EXCEPTION_LOCALE = "data_validation_failed";
    private static final String GIFT_NOT_FOUND_LOCALE = "gifts.not_found";
    private static final String TAG_NOT_FOUND_LOCALE = "tags.not_found";

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e, Locale locale) {

        String errorMessage = messageSource.getMessage(RUNTIME_EXCEPTION_MESSAGE_LOCALE, new Object[]{}, locale);
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e, Locale locale) {

        String errorMessage = messageSource.getMessage(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_LOCALE, new Object[]{}, locale);
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GiftCertificateDataValidationException.class)
    public ResponseEntity<ExceptionResponse> handleGiftCertificateDataValidationException(
            GiftCertificateDataValidationException e, Locale locale) {

        String errorMessage = messageSource.getMessage(DATA_VALIDATION_EXCEPTION_LOCALE, new Object[]{}, locale);
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TagDataValidationException.class)
    public ResponseEntity<ExceptionResponse> handleTagDataValidationException(
            TagDataValidationException e, Locale locale) {

        String errorMessage = messageSource.getMessage(DATA_VALIDATION_EXCEPTION_LOCALE, new Object[]{}, locale);
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GiftCertificateNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleGiftCertificateNotFoundException(
            GiftCertificateNotFoundException e, Locale locale) {

        String errorMessage = messageSource.getMessage(
                GIFT_NOT_FOUND_LOCALE, new Object[]{e.getNotFoundParameter()}, locale);

        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTagNotFoundException(TagNotFoundException e, Locale locale) {

        String errorMessage = messageSource.getMessage(
                TAG_NOT_FOUND_LOCALE, new Object[]{e.getNotFoundParameter()}, locale);

        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}