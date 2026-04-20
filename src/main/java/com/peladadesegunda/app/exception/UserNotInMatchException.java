package com.peladadesegunda.app.exception;

public class UserNotInMatchException extends Exception {
  private static final String ERROR_MESSAGE = "User [%s] not found in this match!";

  public UserNotInMatchException(String username) {
    super(String.format(ERROR_MESSAGE, username));
  }
}
