package com.ticketmonster.movie_beast.services;

import com.ticketmonster.movie_beast.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserService {

    ResponseEntity<?> deleteUserAndCleanup(Authentication authentication, Integer userId);
    ResponseEntity<?> createNewUser(User user);
    ResponseEntity<?> findAllUsers();
    ResponseEntity<?> findSingleUser(Authentication authentication, Integer userId);
    ResponseEntity<?> getUserBasket(Authentication authentication, Integer userId);
    ResponseEntity<?> getAllUserBookings(Authentication authentication, Integer userId);
    ResponseEntity<?> getSingleUserBooking(Authentication authentication, Integer userId, Integer bookingId);
    ResponseEntity<?> updateSingleUser(Authentication authentication, Integer userId, User userDetails);
}
