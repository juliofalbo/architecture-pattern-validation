package com.juliofalbo.poc.archunit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationApiException extends RuntimeException {
    public ValidationApiException(String message) {
        super(message);
    }
}
