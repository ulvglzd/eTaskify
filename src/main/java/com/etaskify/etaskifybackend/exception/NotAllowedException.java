package com.etaskify.etaskifybackend.exception;

import org.springframework.security.access.AccessDeniedException;

public class NotAllowedException extends RuntimeException {
    public NotAllowedException(String msg) {
        super(msg);
    }
}
