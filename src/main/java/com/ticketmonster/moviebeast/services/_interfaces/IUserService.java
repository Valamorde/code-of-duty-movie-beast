package com.ticketmonster.moviebeast.services._interfaces;

import com.ticketmonster.moviebeast.models.Booking;
import com.ticketmonster.moviebeast.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.security.Principal;

public interface IUserService {

    ResponseEntity<?> deleteUserAndCleanup(Authentication authentication, User user);

    ResponseEntity<?> createNewUser(User user);

    ResponseEntity<?> logoutUser(Authentication authentication);

    ResponseEntity<?> getAllUsers();

    ResponseEntity<?> getSingleUser(Authentication authentication, User targetUser);

    ResponseEntity<?> getLoggedUser(Principal principal);

    ResponseEntity<?> getUserBasket(Authentication authentication, User targetUser);

    ResponseEntity<?> getAllUserBookings(Authentication authentication, User targetUser);

    ResponseEntity<?> getSingleUserBooking(Authentication authentication, User targetUser, Booking booking);

    ResponseEntity<?> updateSingleUser(Authentication authentication, User targetUser, User userDetails);

    ResponseEntity<?> resetPassword(Authentication authentication, User targetUser);
}
