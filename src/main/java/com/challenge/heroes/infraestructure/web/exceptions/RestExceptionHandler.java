package com.challenge.heroes.infraestructure.web.exceptions;

import com.challenge.heroes.application.usecase.exceptions.HeroeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(HeroeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleApiException(HeroeNotFoundException ex) {
        ErrorResponse response = new ErrorResponse("error-1", "No se encontro el Heroe con id " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}