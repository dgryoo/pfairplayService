package com.example.pfairplayservice.common.exception.deprecated;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MatchTimeOverlapException extends RuntimeException {
    public MatchTimeOverlapException(String message) {
        super(message);
    }
}
