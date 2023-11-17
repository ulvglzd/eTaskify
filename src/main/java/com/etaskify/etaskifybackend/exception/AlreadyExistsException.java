package com.etaskify.etaskifybackend.exception;

import java.util.Map;

public class AlreadyExistsException extends RuntimeException {

    private final Map<String, Boolean> additionalDetails;

    public AlreadyExistsException(String message, Map<String, Boolean> additionalDetails) {
        super(message);
        this.additionalDetails = additionalDetails;
    }

    public Map<String, Boolean> getAdditionalDetails() {
        return additionalDetails;
    }
}
