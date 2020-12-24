package com.ticketmonster.moviebeast.service;

import com.ticketmonster.moviebeast.model.Booking;
import com.ticketmonster.moviebeast.model.SeatReservation;
import com.ticketmonster.moviebeast.model.User;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;

public interface UserService {

    void deleteUser(Authentication authentication, User user);

    void createNewUser(User user);

    void logoutUser(Authentication authentication);

    List<User> getAllUsers();

    User getSingleUser(Authentication authentication, User targetUser);

    User getLoggedUser(Principal principal);

    List<SeatReservation> getUserBasket(Authentication authentication, User targetUser);

    List<Booking> getBookings(Authentication authentication, User targetUser);

    Booking getBooking(Authentication authentication, Long bookingId);

    void updateSingleUser(Authentication authentication, User targetUser, User userDetails);

    void resetPassword(Authentication authentication, User targetUser);
}
