package com.ticketmonster.moviebeast.controllers.rest;

import com.ticketmonster.moviebeast.controllers.middleware.UserMediator;
import com.ticketmonster.moviebeast.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

/**
 * This Rest Controller is responsible for the Users.
 * It allows the user to create an account and the admin to update or delete a user and reset a password.
 *
 * @author nancyatnic
 */
@Component
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

    @Autowired
    private UserMediator userMediator;

    //<editor-fold desc="User Operations">

    /**
     * Returns the logged user
     *
     * @param user
     * @return the logged user
     */
    @RequestMapping(value = "/loggedUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public Principal loggedUser(Principal user) {
        return user;
    }

    /**
     * Allows the user to create an account
     *
     * @param newUser - requires email, password, full name
     * @return created user, 201 OR CustomException, 409
     */
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerNewUser(@RequestBody User newUser) {
        try {
            return userMediator.createNewUser(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param userId - INTEGER, a user's id
     * @return specified user's details
     */
    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSingleUser(@PathVariable(value = "userId") Integer userId) {
        try {
            return userMediator.getSingleUser(SecurityContextHolder.getContext().getAuthentication(), userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param userId - INTEGER, a user's id
     * @return specified user's basket of seat reservations
     */
    @GetMapping(value = "/users/{userId}/basket", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserBasket(@PathVariable(value = "userId") Integer userId) {
        try {
            return userMediator.getUserBasket(SecurityContextHolder.getContext().getAuthentication(), userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param userId - INTEGER, a user's id
     * @return specified user's bookings
     */
    @GetMapping(value = "/users/{userId}/bookings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUserBookings(@PathVariable(value = "userId") Integer userId) {
        try {
            return userMediator.getAllUserBookings(SecurityContextHolder.getContext().getAuthentication(), userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the user to print their booked tickets
     *
     * @param req - Http Request Properties
     * @param res - Http Response Properties
     * @return
     */
    @GetMapping(value = "/users/{userId}/bookings/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> printTickets(HttpServletRequest req, HttpServletResponse res) {
        userMediator.printTicket(SecurityContextHolder.getContext().getAuthentication(), res, req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param userId    - INTEGER, a user's id
     * @param bookingId - INTEGER, a booking's id
     * @return specified user's specified booking
     */
    @GetMapping(value = "/users/{userId}/bookings/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSingleUserBooking(@PathVariable(value = "userId") Integer userId, @PathVariable(value = "bookingId") Integer bookingId) {
        try {
            return userMediator.getSingleUserBooking(SecurityContextHolder.getContext().getAuthentication(), userId, bookingId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Admin Operations">

    /**
     * @return a list of all users
     */
    @GetMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        try {
            return userMediator.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the admin to update a user's info
     *
     * @param userId
     * @param userDetails
     * @return saves updates, or status error
     */
    @PutMapping(value = "/admin/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable(value = "userId") Integer userId, @Valid @RequestBody User userDetails) {
        try {
            return userMediator.updateSingleUser(SecurityContextHolder.getContext().getAuthentication(), userId, userDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the admin to reset a user's password
     *
     * @param userId
     * @return saves updates, or status error
     */
    @PutMapping(value = "/admin/users/{userId}/passwordReset", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(@PathVariable(value = "userId") Integer userId) {
        try {
            return userMediator.resetPassword(SecurityContextHolder.getContext().getAuthentication(), userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows admin to delete a user
     *
     * @param userId
     * @return deletes user (status ok), or status error
     */
    @DeleteMapping(value = "/admin/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable(value = "userId") Integer userId) {
        try {
            return userMediator.deleteUserAndCleanup(SecurityContextHolder.getContext().getAuthentication(), userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //</editor-fold>
}
