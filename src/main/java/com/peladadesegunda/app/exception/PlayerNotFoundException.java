package com.peladadesegunda.app.exception;

public class PlayerNotFoundException extends Exception {
    private static final String ERROR_MESSAGE = "Player [%s] not found!";

    public PlayerNotFoundException(String id) {
        super(String.format(ERROR_MESSAGE, id));
    }
}
