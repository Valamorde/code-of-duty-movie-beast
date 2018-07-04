package com.ticketmonster.movie_beast.helpers.security.exceptions;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
