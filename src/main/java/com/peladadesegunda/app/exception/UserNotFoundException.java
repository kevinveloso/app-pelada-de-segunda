package com.peladadesegunda.app.exception;

public class UserNotFoundException extends Exception {
    private static final String ERROR_MESSAGE = "User [%s] not found!";

    public UserNotFoundException(String id) {
        super(String.format(ERROR_MESSAGE, id));
    }
}
