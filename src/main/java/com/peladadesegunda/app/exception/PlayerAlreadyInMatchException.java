package com.peladadesegunda.app.exception;

public class PlayerAlreadyInMatchException extends Exception {

    private static final String ERROR_MESSAGE = "User [%s] is already registered in this match!";

    public PlayerAlreadyInMatchException(String username) {
        super(String.format(ERROR_MESSAGE, username));
    }
}
