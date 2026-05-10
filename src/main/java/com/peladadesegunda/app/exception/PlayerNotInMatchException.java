package com.peladadesegunda.app.exception;

public class PlayerNotInMatchException extends Exception {
  private static final String ERROR_MESSAGE = "Player [%s] not found in this match!";

  public PlayerNotInMatchException(String username) {
    super(String.format(ERROR_MESSAGE, username));
  }
}
