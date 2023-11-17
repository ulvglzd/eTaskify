package com.etaskify.etaskifybackend.exception;

public class AuthFailedException extends RuntimeException{

    public AuthFailedException() {
    }

    public AuthFailedException(String message) {
        super(message);
    }
}
