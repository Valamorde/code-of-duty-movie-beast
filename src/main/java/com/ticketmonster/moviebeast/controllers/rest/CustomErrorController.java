package com.ticketmonster.moviebeast.controllers.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * This Rest Controller is responsible for handling the custom errors on the server.
 * Depending on the status code, it returns a message on the web page, stating the nature of the error.
 * @author nancyatnic
 */
@RestController("/error")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping(value = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "error-401";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error-403";
            }else if(statusCode == HttpStatus.BAD_REQUEST.value()){
                return "error-400";
            }
        }
        return "error";
    }
}
