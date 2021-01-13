package com.example.pfairplayservice.commonException;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredParamNotFoundException extends RuntimeException {
    public RequiredParamNotFoundException(String message) {
        super(message);
    }
}
