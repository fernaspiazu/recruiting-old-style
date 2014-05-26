package it.f2informatica.pagination.services;

public class PaginationException extends RuntimeException {
  private static final long serialVersionUID = 5019291640473837236L;

  public PaginationException(String message) {
    super(message);
  }

  public PaginationException(String message, Throwable cause) {
    super(message, cause);
  }

}