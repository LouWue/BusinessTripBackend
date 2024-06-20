package org.example.businesstripps.security.exceptions;

import java.util.List;
import java.util.Map;

public class UserAlreadyExistsException extends RuntimeException {
    private final Map<String, List<String>> errors;

    public UserAlreadyExistsException(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
