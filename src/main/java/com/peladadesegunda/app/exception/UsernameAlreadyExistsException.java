package com.peladadesegunda.app.exception;

public class UsernameAlreadyExistsException extends Exception {

    private static final String ERROR_MESSAGE = "Username [%s] is already registered.";

    public UsernameAlreadyExistsException(String username) {
        super(String.format(ERROR_MESSAGE, username));
    }
}
