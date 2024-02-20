package com.example.cs2sw.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{
    private final HttpStatus code;


    public CustomException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }


    public HttpStatus getCode() {
        return code;
    }
}
