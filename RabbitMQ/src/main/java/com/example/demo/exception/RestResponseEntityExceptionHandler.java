package com.example.demo.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity illegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());

        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST,
                                       request);
    }

    @ResponseBody
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity objectNotFound(ObjectNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());

        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.NOT_FOUND,
                                       request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        String fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                .collect(joining(", "));

        String objectErrors = ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(error -> String.format("%s: %s", error.getObjectName(),
                                            error.getDefaultMessage()))
                .collect(joining(", "));

        //concatenate validation errors
        String ss = Stream.of(fieldErrors, objectErrors)
                .filter(s -> s != null && !s.isEmpty())
                .collect(joining(", "));

        return handleExceptionInternal(ex, new ErrorMessage(ss), new HttpHeaders(),
                                       HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new ErrorMessage(ex.getCause().getMessage()),
                                       new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}

