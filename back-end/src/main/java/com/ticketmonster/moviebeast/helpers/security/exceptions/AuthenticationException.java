package com.ticketmonster.moviebeast.helpers.security.exceptions;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
