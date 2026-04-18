package com.peladadesegunda.app.exception;

public class MatchNotFoundException extends Exception {

    private static final String ERROR_MESSAGE = "Match [%s] not found!";


    public MatchNotFoundException(String id) {
        super(String.format(ERROR_MESSAGE, id));
    }
}
