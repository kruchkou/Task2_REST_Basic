package com.epam.esm.controller.advice;

import com.epam.esm.controller.model.util.ExceptionResponse;
import com.epam.esm.service.exception.impl.*;
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
    private static final String GIFT_BY_PARAM_NOT_FOUND_LOCALE = "gifts.by_param_not_found";
    private static final String GIFT_BY_PARAMS_NOT_FOUND_LOCALE = "gifts.by_params_not_found";
    private static final String TAG_NOT_FOUND_LOCALE = "tags.not_found";
    private static final String USER_NOT_FOUND_LOCALE = "users.not_found";
    private static final String ORDER_NOT_FOUND_LOCALE = "orders.not_found";
    private static final String TAG_ALREADY_EXISTS_LOCALE = "tags.already_exists";

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e, Locale locale) {

        String errorMessage = messageSource.getMessage(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_LOCALE, new Object[]{}, locale);
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataValidationException.class)
    public ResponseEntity<ExceptionResponse> handleDataValidationException(
            DataValidationException e, Locale locale) {

        String errorMessage = messageSource.getMessage(DATA_VALIDATION_EXCEPTION_LOCALE, new Object[]{}, locale);
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GiftCertificateByParameterNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleGiftCertificateNotFoundException(
            GiftCertificateByParameterNotFoundException e, Locale locale) {

        String errorMessage = messageSource.getMessage(
                GIFT_BY_PARAM_NOT_FOUND_LOCALE, new Object[]{e.getNotFoundParameter()}, locale);

        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GiftCertificateByParametersNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleGiftCertificateNotFoundException(
            GiftCertificateByParametersNotFoundException e, Locale locale) {

        String errorMessage = messageSource.getMessage(
                GIFT_BY_PARAMS_NOT_FOUND_LOCALE, new Object[]{}, locale);

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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException e, Locale locale) {

        String errorMessage = messageSource.getMessage(
                USER_NOT_FOUND_LOCALE, new Object[]{e.getNotFoundParameter()}, locale);

        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleOrderNotFoundException(UserNotFoundException e, Locale locale) {

        String errorMessage = messageSource.getMessage(
                ORDER_NOT_FOUND_LOCALE, new Object[]{e.getNotFoundParameter()}, locale);

        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TagAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleTagAlreadyExistsException(TagAlreadyExistsException e, Locale locale) {

        String errorMessage = messageSource.getMessage(
                TAG_ALREADY_EXISTS_LOCALE, new Object[]{}, locale);

        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage, e.getErrorCode());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }



    //@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e, Locale locale) {

        String errorMessage = messageSource.getMessage(RUNTIME_EXCEPTION_MESSAGE_LOCALE, new Object[]{}, locale);
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorMessage);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}