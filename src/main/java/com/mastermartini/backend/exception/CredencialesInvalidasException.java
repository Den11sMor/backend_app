package com.mastermartini.backend.exception;
public class CredencialesInvalidasException extends RuntimeException {
  public CredencialesInvalidasException(String mensaje) { super(mensaje); }
}
