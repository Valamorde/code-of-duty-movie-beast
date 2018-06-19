package com.ticketmonster.movie_beast.custom_exceptions;

public class CustomException {

    private String errorMessage;

    public CustomException(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

