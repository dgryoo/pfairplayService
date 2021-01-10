package com.example.pfairplayservice.member;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class requiredParamNotFoundException extends RuntimeException {
    public requiredParamNotFoundException(String message) {
        super(message);
    }
}
