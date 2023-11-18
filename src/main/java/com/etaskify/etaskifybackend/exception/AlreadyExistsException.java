package com.etaskify.etaskifybackend.exception;

import java.util.Map;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String message) {
        super(message);
    }

}
