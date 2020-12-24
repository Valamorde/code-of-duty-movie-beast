package com.ticketmonster.moviebeast.rest;

import com.ticketmonster.moviebeast.model.Booking;
import com.ticketmonster.moviebeast.model.SeatReservation;
import com.ticketmonster.moviebeast.model.User;
import com.ticketmonster.moviebeast.rest.middleware.UserMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController extends AbstractController {

    @Autowired
    private UserMediator userMediator;

    @RequestMapping(
            value = "/loggedUser",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public User loggedUser(Principal principal) {
        return userMediator.getLoggedUser(principal);
    }

    @PostMapping(
            value = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registerNewUser(@RequestBody User newUser) {
        userMediator.createNewUser(newUser);
    }

    @GetMapping(value = "/logoutUser")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logoutUser() {
        userMediator.logoutUser(SecurityContextHolder.getContext().getAuthentication());
    }

    @GetMapping(
            value = "/users/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public User getUser(@PathVariable(value = "userId") Long userId) {
        return userMediator.getUser(SecurityContextHolder.getContext().getAuthentication(), userId);
    }

    @GetMapping(
            value = "/users/{userId}/basket",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<SeatReservation> getUserBasket(@PathVariable(value = "userId") Long userId) {
        return userMediator.getUserBasket(SecurityContextHolder.getContext().getAuthentication(), userId);
    }

    @GetMapping(
            value = "/users/{userId}/bookings",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Booking> getUserBookings(@PathVariable(value = "userId") Long userId) {
        return userMediator.getBookings(SecurityContextHolder.getContext().getAuthentication(), userId);
    }

    @GetMapping(
            value = "/users/{userId}/bookings/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public void printTickets(HttpServletRequest req, HttpServletResponse res) {
        userMediator.printTicket(
                SecurityContextHolder.getContext().getAuthentication(),
                res,
                req
        );
    }

    @GetMapping(
            value = "/admin/users",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<User> getAllUsers() {
        return userMediator.getAllUsers();
    }

    @PutMapping(
            value = "/admin/users/{userId}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(
            @PathVariable(value = "userId") Long userId,
            @RequestBody User userDetails) {

        userMediator.updateSingleUser(
                SecurityContextHolder.getContext().getAuthentication(),
                userId,
                userDetails
        );
    }

    @PutMapping(
            value = "/admin/users/{userId}/passwordReset"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@PathVariable(value = "userId") Long userId) {
        userMediator.resetPassword(
                SecurityContextHolder.getContext().getAuthentication(),
                userId
        );
    }

    @DeleteMapping(
            value = "/admin/users/{userId}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "userId") Long userId) {
        userMediator.deleteUser(
                SecurityContextHolder.getContext().getAuthentication(),
                userId
        );
    }
}
