package com.peladadesegunda.app.exception;

public class MatchIsOverException  extends Exception {

    private static final String ERROR_MESSAGE = "Match [%s] is OVER!";

    public MatchIsOverException(String id) {
        super(String.format(ERROR_MESSAGE, id));
    }
}
