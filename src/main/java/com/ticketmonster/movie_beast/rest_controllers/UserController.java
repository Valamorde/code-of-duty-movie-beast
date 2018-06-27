package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.helpers.config.CustomAccessHandler;
import com.ticketmonster.movie_beast.helpers.custom_exceptions.CustomException;
import com.ticketmonster.movie_beast.helpers.custom_exceptions.ResourceNotFoundException;
import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.Role;
import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.IBookingRepository;
import com.ticketmonster.movie_beast.repositories.ISeatReservationRepository;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import java.security.Principal;
import java.util.List;

@Component
@RestController
public class UserController {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ISeatReservationRepository seatReservationRepository;

    @Autowired
    IBookingRepository bookingRepository;

    @Autowired
    CustomAccessHandler customAccessHandler;

    /**
     * Allows the user to Login
     *
     * @param user - email and password
     * @return logged in user
     */
    @PostMapping("/login")
    public Principal user(Principal user) {

        return user;
    }

    /**
     * Allows the user to create an account
     *
     * @param newUser - requires email, password, fullname
     * @return created user, 201 OR CustomException, 409
     */
    @PostMapping("/register")
    @Produces("application/json")
    public ResponseEntity<?> createUser(@Valid @RequestBody User newUser) {
        if (userRepository.findByEmail(newUser.getEmail()) != null) {
            return new ResponseEntity<>(
                    new CustomException("Sorry but email: <" + newUser.getEmail() + "> already exists."),
                    HttpStatus.CONFLICT);
        }
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setRole(Role.USER.name());
        newUser.setEnabled(true);
        return new ResponseEntity<User>(userRepository.save(newUser), HttpStatus.CREATED);
    }

    /**
     * @return a list of registered users
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * @param uId - INTEGER, a user's id
     * @return specified user's details
     */
    @GetMapping("/users/{uid}")
    public User getUserById(@PathVariable(value = "uid") Integer uId) {
        return userRepository.findById(uId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", uId));
    }

    /**
     * @param uId - INTEGER, a user's id
     * @return specified user's basket of seat reservations
     */
    @GetMapping("/users/{uid}/basket")
    public ResponseEntity<?> getUserBasket(@PathVariable(value = "uid") Integer uId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());

        if (customAccessHandler.isUserAuthorizedToViewSpecifiedId(uId, user)) {
            return new ResponseEntity<>(seatReservationRepository.findAllBySeatReservedIsTrueAndSeatPaidIsFalseAndUserIdIs(uId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param uId - INTEGER, a user's id
     * @return specified user's bookings
     */
    @GetMapping("/users/{uid}/bookings")
    public ResponseEntity<?> getAllUserBookings(@PathVariable(value = "uid") Integer uId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());

        if (customAccessHandler.isUserAuthorizedToViewSpecifiedId(uId, user)) {
            return new ResponseEntity<>(bookingRepository.findAllByUserId(uId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param uId - INTEGER, a user's id
     * @param bId - INTEGER, a booking's id
     * @return specified user's specified booking
     */
    @GetMapping("/users/{uid}/bookings/{bid}")
    public ResponseEntity<?> getSingleUserBooking(@PathVariable(value = "uid") Integer uId, @PathVariable(value = "bid") Integer bId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());

        if (customAccessHandler.isUserAuthorizedToViewSpecifiedId(uId, user)) {
            return new ResponseEntity<>(bookingRepository.findByBookingIdAndUserId(bId, uId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    // Update a User
    @PutMapping("/users/{uid}")
    public User updateUser(@PathVariable(value = "uid") Integer id, @Valid @RequestBody User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        user.setFullName(userDetails.getFullName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    // Delete a User
    @DeleteMapping("/users/{uid}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "uid") Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
