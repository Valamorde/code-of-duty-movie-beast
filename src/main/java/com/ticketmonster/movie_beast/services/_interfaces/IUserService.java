package com.ticketmonster.movie_beast.services._interfaces;

import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface IUserService {

    ResponseEntity<?> deleteUserAndCleanup(Authentication authentication, User user);

    ResponseEntity<?> createNewUser(User user);

    ResponseEntity<?> getAllUsers();

    ResponseEntity<?> getSingleUser(Authentication authentication, User targetUser);

    ResponseEntity<?> getUserBasket(Authentication authentication, User targetUser);

    ResponseEntity<?> getAllUserBookings(Authentication authentication, User targetUser);

    ResponseEntity<?> getSingleUserBooking(Authentication authentication, User targetUser, Booking booking);

    ResponseEntity<?> updateSingleUser(Authentication authentication, User targetUser, User userDetails);

    ResponseEntity<?> resetPassword(Authentication authentication, User targetUser);
}
