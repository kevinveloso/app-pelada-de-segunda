package com.peladadesegunda.app.exception;

public class MatchCreationNotAllowed  extends Exception {

    private static final String ERROR_MESSAGE = "Match creation not allowed! Please review dates.";

    public MatchCreationNotAllowed() {
        super(String.format(ERROR_MESSAGE));
    }
}
